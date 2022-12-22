package org.gefsu;

import org.gefsu.http.HttpRequest;
import org.gefsu.http.HttpResponse;

public class RequestHandlerDispatcher {
    private final HandlerMap requestHandlers;

    public RequestHandlerDispatcher(HandlerMap requestHandlers) {
        this.requestHandlers = requestHandlers;
    }

    public HttpResponse handleRequest(HttpRequest request) {
        final var requestMethod = request.getMethod();
        final var requestResource = request.getResource();

        return requestHandlers.invokeHandler(requestMethod, requestResource);
    }
}
