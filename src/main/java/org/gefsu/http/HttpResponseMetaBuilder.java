package org.gefsu.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO If methods return this builder you can chain them
public class HttpResponseMetaBuilder {

    int statusCode;
    Map<String, List<String>> headers;

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    private void addHeader(String key, String value) {
        if (headers == null)
            headers = new HashMap<>();

        if (!headers.containsKey(key))
            headers.put(key, List.of(value));
        else
            headers.replace(key, List.of(value));

    }

    public void setMimeType(String mimeType) {
        addHeader("Content-Type", mimeType);
    }

    public HttpResponseMeta build() {
        return new HttpResponseMeta(statusCode, headers);
    }

}
