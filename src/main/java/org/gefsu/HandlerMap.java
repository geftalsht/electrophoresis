package org.gefsu;

import org.gefsu.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HandlerMap {
    private final Map<HttpMethod,List<Pair<String,Pair<Object,Method>>>> requestHandlers;

    public Map<HttpMethod, List<Pair<String, Pair<Object, Method>>>> getRequestHandlers() {
        return requestHandlers;
    }

    public static HandlerMap create(final List<Object> controllerObjects) {
        final var parsed = Arrays.stream(HttpMethod.values())
            .collect(Collectors.toMap(
                httpMethod -> httpMethod,
                httpMethod -> findHandlers(httpMethod, controllerObjects)));

        return new HandlerMap(parsed);
    }

    private static List<Pair<String, Pair<Object,Method>>> findHandlers(
        HttpMethod httpMethod, List<Object> controllerObjects)
    {
        return controllerObjects
            .stream()
            .flatMap(controller -> Arrays.stream(controller
                .getClass()
                .getMethods())
                .filter(method -> method.isAnnotationPresent(HttpRequestMapping.class))
                .filter(method -> method
                    .getAnnotation(HttpRequestMapping.class)
                    .method()
                    .equals(httpMethod))
                .map(method -> Pair.of(controller, method)))
            .map(methodPair -> Pair.of(
                methodPair
                    .getRight()
                    .getAnnotation(HttpRequestMapping.class)
                    .url(),
                methodPair))
            .toList();
    }

    private HandlerMap(final Map<HttpMethod,List<Pair<String,Pair<Object,Method>>>> parsedHandlers) {
        requestHandlers = parsedHandlers;
    }
}
