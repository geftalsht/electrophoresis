package org.gefsu;

import org.gefsu.http.HttpRequest;
import org.gefsu.http.HttpResponse;
import org.gefsu.http.HttpResponseBuilder;

import java.util.Arrays;

import static org.gefsu.OptionalUtils.lift;

public class RequestHandlerDispatcher {
    ScuffedController controller;

    public RequestHandlerDispatcher(ScuffedController controller) {
        this.controller = controller;
    }

    public HttpResponse handleRequest(HttpRequest request) {
        final var requestMethod = request.getMethod();
        final var requestResource = request.getResource();

        return Arrays.stream(controller
            .getClass()
            .getMethods())
            .filter(method -> method.isAnnotationPresent(HttpRequestMapping.class))
            .filter(method ->
                method.getDeclaredAnnotation(HttpRequestMapping.class)
                    .method()
                    .equals(requestMethod) &&
                requestResource.matches(
                    method.getDeclaredAnnotation(HttpRequestMapping.class)
                        .url()
                )
            )
            .filter(method -> method.canAccess(controller))
            .findFirst()
            .flatMap(method -> lift(
                () -> (HttpResponse) method.invoke(requestResource)))
            .orElseGet(() -> new HttpResponseBuilder()
                .buildSimpleErrorResponse(500));
    }
}
