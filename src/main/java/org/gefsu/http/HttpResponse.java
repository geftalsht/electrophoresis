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

        return version() +
            " " +
            statusCode() +
            " " +
            statusMessage(statusCode) +
            "\r\n" +
            headers() +
            body();
    }

    public interface Builder<T> {

        public Builder<T> statusCode(int statusCode);

        public Builder<T> headers(Map<String, List<String>> headers);

        public Builder<T> header(String key, List<String> values);

        public Builder<T> header(String key, String value);

        public Builder<T> body(T body);

        public HttpResponse<T> build();

    }

    private String version() {
        return "HTTP/1.1";
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
