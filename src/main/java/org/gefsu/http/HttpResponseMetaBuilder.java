package org.gefsu.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponseMetaBuilder {

    int statusCode;
    Map<String, List<String>> headers;

    public HttpResponseMetaBuilder setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    private HttpResponseMetaBuilder addHeader(String key, String value) {
        if (headers == null)
            headers = new HashMap<>();

        if (!headers.containsKey(key))
            headers.put(key, List.of(value));
        else
            headers.replace(key, List.of(value));

        return this;
    }

    public HttpResponseMetaBuilder setMimeType(String mimeType) {
        addHeader("Content-Type", mimeType);
        return this;
    }

    public HttpResponseMeta build() {
        return new HttpResponseMeta(this);
    }

}
