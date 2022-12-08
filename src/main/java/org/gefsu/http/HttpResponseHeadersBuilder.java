package org.gefsu.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponseHeadersBuilder {
    int statusCode;
    Map<String, List<String>> headers;

    public HttpResponseHeadersBuilder setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    private HttpResponseHeadersBuilder addHeader(String key, String value) {
        if (headers == null)
            headers = new HashMap<>();

        if (!headers.containsKey(key))
            headers.put(key, List.of(value));
        else
            headers.replace(key, List.of(value));

        return this;
    }

    public HttpResponseHeadersBuilder setMimeType(String mimeType) {
        addHeader("Content-Type", mimeType);
        return this;
    }

    public HttpResponseHeaders build() {
        return new HttpResponseHeaders(this);
    }
}
