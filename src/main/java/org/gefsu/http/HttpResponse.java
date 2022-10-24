package org.gefsu.http;

import java.util.List;
import java.util.Map;

public class HttpResponse<T> {
    private final int statusCode;
    private final Map<String, List<String>> headers;
    private final T body;

    public HttpResponse(HttpResponseBuilderImpl<T> builder) {
        this.statusCode = builder.statusCode;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    @Override
    public String toString() {

        return "HTTP/1.1" +
            " " +
            statusCode() +
            " " +
            statusMessage(statusCode) +
            "\r\n" +
            headers() +
            body();
    }

    public interface Builder<T> {

        Builder<T> statusCode(int statusCode);

        Builder<T> headers(Map<String, List<String>> headers);

        Builder<T> header(String key, List<String> values);

        Builder<T> header(String key, String value);

        Builder<T> body(T body);

        HttpResponse<T> build();

    }

    private int statusCode() {
        return statusCode;
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

    private String body() {

        if (body == null)
            return "";

        return body.toString();
    }

}
