package org.gefsu.http;

import java.util.List;
import java.util.Map;

public class HttpResponse<T> {
    private int statusCode;
    private Map<String, List<String>> headers;
    private T body;

    public HttpResponse(HttpResponseBuilderImpl<T> builder) {
        this.statusCode = builder.statusCode;
        this.headers = builder.headers;
        this.body = builder.body;
    }

    public interface Builder<T> {

        public Builder<T> statusCode(int statusCode);

        public Builder<T> headers(Map<String, List<String>> headers);

        public Builder<T> header(String key, List<String> values);

        public Builder<T> header(String key, String value);

        public Builder<T> body(T body);

        public HttpResponse<T> build();

    }

}
