package org.gefsu.http.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponseBuilderImpl implements HttpResponse.Builder {

    int statusCode;
    Map<String, List<String>> headers;
    byte[] body;

    @Override
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public void addHeaders(Map<String, List<String>> headers) {
        if (headers == null)
            headers = new HashMap<>();

        this.headers.putAll(headers);
    }

    @Override
    public void addHeader(String key, List<String> values) {
        if (headers == null)
            headers = new HashMap<>();

        this.headers.put(key, values);
    }

    @Override
    public void addHeader(String key, String value) {
        if (headers == null)
            headers = new HashMap<>();

        headers.put(key, List.of(value));
    }

    @Override
    public void setMimeType(String ekse) {
        // FIXME Not implemented
    }

    @Override
    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public HttpResponse build() {
        return new HttpResponse(statusCode, headers, body);
    }

}
