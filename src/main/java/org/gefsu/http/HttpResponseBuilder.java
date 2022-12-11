package org.gefsu.http;

import org.gefsu.ServerSettings;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpResponseBuilder {
    int statusCode;
    Map<String, List<String>> headers;
    URI uri = null;

    public HttpResponseBuilder setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    private HttpResponseBuilder addHeader(String key, String value) {
        if (headers == null)
            headers = new HashMap<>();

        if (!headers.containsKey(key))
            headers.put(key, List.of(value));
        else
            headers.replace(key, List.of(value));

        return this;
    }

    public HttpResponseBuilder setMimeType(String mimeType) {
        addHeader("Content-Type", mimeType);
        return this;
    }

    public HttpResponseBuilder setUri(URI uri) {
        this.uri = uri;
        return this;
    }

    public HttpResponse build() {
        return new HttpResponse(this);
    }

    public HttpResponse buildGetOKResponse(URI uri, ServerSettings settings) {
        setStatusCode(200);
        setUri(uri);
        setMimeType(
            settings.determineMimeType(new File(uri).getName())
        );
        return build();
    }

    public HttpResponse buildSimpleErrorResponse(int statusCode) {
        setStatusCode(statusCode);
        return build();
    }
}
