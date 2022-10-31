package org.gefsu.http;

import java.util.List;
import java.util.Map;

public class HttpResponseBuilderImpl<T> implements HttpResponse.Builder<T> {

    int responseCode;
    Map<String, List<String>> headers;
    T body;

    @Override
    public void setStatusCode(int statusCode) {
        responseCode = statusCode;
    }

    @Override
    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    @Override
    public void addHeader(String key, List<String> value) {

    }

    @Override
    public void addHeader(String key, String value) {

    }

    @Override
    public void setBody(T body) {

    }

    @Override
    public HttpResponse<T> build() {
        return new HttpResponse<>(responseCode, headers, body);
    }

}
