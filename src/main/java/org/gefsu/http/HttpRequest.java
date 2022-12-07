package org.gefsu.http;

public class HttpRequest {
    private final HttpMethod method;
    private final String resource;

    HttpRequest(HttpMethod method, String resource) {
        this.method = method;
        this.resource = resource;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getResource() {
        return resource;
    }
}
