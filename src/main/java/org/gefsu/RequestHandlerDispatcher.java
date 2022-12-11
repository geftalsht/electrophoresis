package org.gefsu;

import org.gefsu.http.HttpRequest;

import java.util.Arrays;
import java.util.Optional;

import static org.gefsu.OptionalUtils.lift;

public class RequestHandlerDispatcher {
    ScuffedController controller;

    public RequestHandlerDispatcher(ScuffedController controller) {
        this.controller = controller;
    }

    public Optional<String> handleRequest(HttpRequest request) {
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
                () -> (String) method.invoke(requestResource))
            );
    }
}
