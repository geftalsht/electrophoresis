package org.gefsu.http;

import java.util.List;
import java.util.Map;

public class HttpResponse<T> {
    private int statusCode;
    private Map<String, List<String>> headers;
    private T body;

    public <T> HttpResponse(HttpResponseBuilderImpl httpResponseBuilder) {

    }

    public interface Builder<T> {

        public Builder<?> statusCode(int statusCode);

        public Builder<?> headers(Map<String, List<String>> headers);

        public Builder<?> header(String key, List<String> values);

        public Builder<?> header(String key, String value);

        public Builder<T> body(T body);

        public HttpResponse<?> build();

    }

}
