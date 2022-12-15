package org.gefsu;

import org.gefsu.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HandlerMap<T> {
    private final Map<HttpMethod,Pair<String,Pair<T,Method>>> requestHandlers;

    public static <T> HandlerMap<T> create(final List<T> controllerObjects) {
        final var parsed = controllerObjects
            .stream()
            .flatMap(controller -> Arrays.stream(controller
                    .getClass()
                    .getMethods())
                .filter(method -> method.isAnnotationPresent(HttpRequestMapping.class))
                .map(method -> Pair.of(controller, method)))
            .map(methodPair -> Pair.of(
                methodPair
                    .getRight()
                    .getAnnotation(HttpRequestMapping.class)
                    .url(),
                methodPair))
            .collect(Collectors.toMap(
                urlpair -> urlpair
                    .getRight()
                    .getRight()
                    .getAnnotation(HttpRequestMapping.class)
                    .method(),
                urlpair -> urlpair));

        return new HandlerMap<>(parsed);
    }

    private HandlerMap(final Map<HttpMethod,Pair<String,Pair<T,Method>>> parsedHandlers) {
        requestHandlers = parsedHandlers;
    }
}
