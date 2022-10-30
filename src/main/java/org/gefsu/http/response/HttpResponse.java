package org.gefsu.http.response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HttpResponse {
    private final int statusCode;
    private final Map<String, List<String>> headers;
    private final byte[] body;

    public HttpResponse(int statusCode, Map<String, List<String>> headers, byte[] body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public interface Builder {
        void setStatusCode(int statusCode);
        void addHeaders(Map<String, List<String>> headers);
        void addHeader(String key, List<String> values);
        void addHeader(String key, String value);
        void setMimeType(String ekse);
        void setBody(byte[] body);
        HttpResponse build();
    }

    // I'm not proud of this, but hey
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

    private String statusCode() {
        return Integer.toString(statusCode);
    }

    // Not proud of this as well
    private String statusMessage(int statusCode) {
        return (
            switch (statusCode) {
                case 200 -> "OK";
                case 400 -> "Bad Request";
                case 403 -> "Forbidden";
                case 404 -> "Not Found";
                case 405 -> "Method Not Allowed";
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
