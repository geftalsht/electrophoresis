package org.gefsu.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponseBuilderImpl implements HttpResponse.Builder {

    int statusCode;
    Map<String, List<String>> headers;
    byte[] body;

    @Override
    public HttpResponse.Builder statusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    @Override
    public HttpResponse.Builder headers(Map<String, List<String>> headers) {
        if (headers == null)
            headers = new HashMap<>();

        this.headers.putAll(headers);
        return this;
    }

    @Override
    public HttpResponse.Builder header(String key, List<String> values) {
        if (headers == null)
            headers = new HashMap<>();

        this.headers.put(key, values);
        return this;
    }

    @Override
    public HttpResponse.Builder header(String key, String value) {
        if (headers == null)
            headers = new HashMap<>();

        headers.put(key, List.of(value));
        return this;
    }

    @Override
    public HttpResponse.Builder mimeType(MimeType mimeType) {
        if (headers != null && headers.containsKey("Content-Key"))
            headers.replace("Content-Key", List.of(mimeType.toString()));

        if (headers == null)
            headers = new HashMap<>();

        headers.put("Content-Type", List.of(mimeType.toString()));
        return this;
    }

    @Override
    public HttpResponse.Builder body(byte[] body) {
        this.body = body;
        return this;
    }

    @Override
    public HttpResponse build() {
        return new HttpResponse(this);
    }

}
