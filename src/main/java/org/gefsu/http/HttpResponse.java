package org.gefsu.http;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HttpResponse {
    final int statusCode;
    final Map<String, List<String>> headers;
    final URI uri;

    HttpResponse(HttpResponseBuilder builder) {
        statusCode = builder.statusCode;
        headers = builder.headers;
        uri = builder.uri;
    }

    private String headersToString() {
        var sb = new StringBuilder();

        sb.append("HTTP/1.1 ")
            .append(statusCode)
            .append(responseMessage(statusCode))
            .append("\r\n");

        if (headers != null) {
            sb.append(httpHeaders());
            sb.append("\r\n");
            sb.append("\r\n");
        }
        return sb.toString();
    }

    public byte[] metaToBytes() {
        return headersToString()
            .getBytes(StandardCharsets.UTF_8);
    }

    private String responseMessage(int responseCode) {
        return
            switch (responseCode) {
                case 200 -> "OK";
                case 400 -> "Bad Request";
                case 403 -> "Forbidden";
                case 404 -> "Not Found";
                case 405 -> "Method Not Allowed";
                default -> "";
            };
    }

    private String httpHeaders() {
        if (headers == null)
            return "";

        var sb = new StringBuilder();

        headers.forEach(
            (k,v) -> {
                sb.append(k)
                    .append(": ");

                var i = v.iterator();
                i.forEachRemaining(value -> {
                    sb.append(value);
                    if (i.hasNext())
                        sb.append("; ");
                });
            });
        return sb.toString();
    }
}
