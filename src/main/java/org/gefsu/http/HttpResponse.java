package org.gefsu.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HttpResponse {
    private final int statusCode;
    private final Map<String, List<String>> headers;
    private final byte[] body;

    public HttpResponse(HttpResponseBuilderImpl builder) {
        this.statusCode = builder.statusCode;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    public byte[] toBytes() throws IOException {
        var baos = new ByteArrayOutputStream();

        baos.write("HTTP/1.1".getBytes());
        baos.write(" ".getBytes());
        baos.write(statusCode().getBytes());
        baos.write(" ".getBytes());
        baos.write(statusMessage(statusCode).getBytes());
        baos.write("\r\n".getBytes());
        if (headers != null)
            baos.write(headers().getBytes());
        if (body != null)
            baos.write(body);

        return baos.toByteArray();
    }

    public interface Builder {

        Builder statusCode(int statusCode);

        Builder headers(Map<String, List<String>> headers);

        Builder header(String key, List<String> values);

        Builder header(String key, String value);

        Builder mimeType(MimeType mimeType);

        Builder body(byte[] body);

        HttpResponse build();

    }

    private String statusCode() {
        return Integer.toString(statusCode);
    }

    private String statusMessage(int statusCode) {
        return (
            switch (statusCode) {
                case 200 -> "OK";
                case 400 -> "Bad Request";
                case 403 -> "Forbidden";
                case 404 -> "Not Found";
                default -> "";
            });
    }

    private String headers() {

        if (headers == null)
            return "";

        var sb = new StringBuilder();
        headers.forEach(
            (k, v) -> {
                sb.append(k)
                    .append(": ");

                var i = v.iterator();
                i.forEachRemaining(value -> {
                    sb.append(value);
                    if (i.hasNext())
                        sb.append("; ");
                });
                sb.append("\r\n")
                    .append("\r\n");
            });

        return sb.toString();
    }

}
