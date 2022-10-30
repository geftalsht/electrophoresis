package org.gefsu.http;

public class ScuffedHttpRequest {

    private final ScuffedHttpMethod method;
    private final String resource;

    ScuffedHttpRequest(ScuffedHttpMethod method, String resource) {
        this.method = method;
        this.resource = resource;
    }

    public ScuffedHttpMethod getMethod() {
        return method;
    }

    public String getResource() {
        return resource;
    }

}
