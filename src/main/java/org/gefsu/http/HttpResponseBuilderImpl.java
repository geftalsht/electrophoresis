package org.gefsu.http;

import java.util.List;
import java.util.Map;

public class HttpResponseBuilderImpl<T> implements HttpResponse.Builder<T> {

    int statusCode;
    Map<String, List<String>> headers;
    T body;

    @Override
    public HttpResponse.Builder<?> statusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    @Override
    public HttpResponse.Builder<?> headers(Map<String, List<String>> headers) {
        this.headers.putAll(headers);
        return this;
    }

    @Override
    public HttpResponse.Builder<?> header(String key, List<String> values) {
        this.headers.put(key, values);
        return this;
    }

    @Override
    public HttpResponse.Builder<?> header(String key, String value) {
        headers.put(key, List.of(value));
        return this;
    }

    @Override
    public HttpResponse.Builder<T> body(T body) {
        this.body = body;
        return this;
    }

    @Override
    public HttpResponse<?> build() {
        return new HttpResponse(this);
    }

}
