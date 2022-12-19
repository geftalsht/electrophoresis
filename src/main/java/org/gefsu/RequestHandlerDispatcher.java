package org.gefsu;

import org.gefsu.http.HttpRequest;
import org.gefsu.http.HttpResponse;
import org.gefsu.http.HttpResponseBuilder;

import static org.gefsu.OptionalUtils.lift;

public class RequestHandlerDispatcher {
    private final HandlerMap requestHandlers;

    public RequestHandlerDispatcher(HandlerMap requestHandlers) {
        this.requestHandlers = requestHandlers;
    }

    @SuppressWarnings("Convert2MethodRef")
    public HttpResponse handleRequest(HttpRequest request) {
        final var requestMethod = request.getMethod();
        final var requestResource = request.getResource();

        return requestHandlers
            .getRequestHandlers()
            .get(requestMethod)
            .stream()
            .filter(pair -> requestResource
                .matches(pair.getLeft()))
            .findFirst()
            .map(pair -> pair.getRight())
            .flatMap(pair ->
                lift(() -> (HttpResponse)
                    pair.getRight().invoke(pair.getLeft(), requestResource)))
            .orElseGet(() -> new HttpResponseBuilder()
                .buildSimpleErrorResponse(500));
    }
}
