package org.gefsu;

import org.gefsu.http.HttpRequest;

import java.util.Arrays;
import java.util.Optional;

import static org.gefsu.OptionalUtils.lift;

public class RequestHandlerDispatcher {
    // Reference to the single controller.
    // Ideally I could maybe (probably) implement controller scanning
    // like in Spring. But for now let's just scan this one controller
    // for methods

    // Creating this with new here is not a good idea, but I don't
    // know how to improve it yet.
    ScuffedController scuffedController = new ScuffedController();

    // Can maybe return an optional ByteInputStream instead, idk
    public Optional<String> handleRequest(HttpRequest request) {
        final var requestMethod = request.getMethod();
        final var requestResource = request.getResource();

        return Arrays.stream(scuffedController
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
            .filter(method -> method.canAccess(scuffedController))
            .findFirst()
            .flatMap(method -> lift(
                () -> (String) method.invoke(requestResource))
            );
    }
}
