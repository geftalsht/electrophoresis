package org.gefsu;

import org.gefsu.annotation.Autowired;
import org.gefsu.annotation.RequestHandlers;
import org.gefsu.http.HttpRequest;
import org.gefsu.http.HttpResponse;

public class RequestHandlerDispatcher {
    @Autowired
    private final RequestHandlers requestHandlers;

    public RequestHandlerDispatcher(RequestHandlers requestHandlers) {
        this.requestHandlers = requestHandlers;
    }

    public HttpResponse handleRequest(HttpRequest request) {
        final var requestMethod = request.getMethod();
        final var requestResource = request.getResource();

        return requestHandlers
            .invokeHandler(requestMethod, requestResource);
    }
}
