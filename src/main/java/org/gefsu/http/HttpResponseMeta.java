package org.gefsu.http;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HttpResponseMeta {

    final int responseCode;
    final Map<String, List<String>> headers;

    HttpResponseMeta(int responseCode, Map<String, List<String>> headers) {
        this.responseCode = responseCode;
        this.headers = headers;
    }

    private String metaToString() {

        var sb = new StringBuilder();

        sb.append("HTTP/1.1 ")
            .append(responseCode)
            .append(responseMessage(responseCode))
            .append("\r\n");

        if (headers != null) {
            sb.append(headersToString());
            sb.append("\r\n");
        }

        return sb.toString();
    }

    private String headersToString() {
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

    public byte[] metaToBytes() {
        return
            metaToString().getBytes(StandardCharsets.UTF_8);
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

}
