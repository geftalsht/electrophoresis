package org.gefsu;

import org.gefsu.http.HttpMethod;
import org.gefsu.http.HttpResponse;
import org.gefsu.http.HttpResponseBuilder;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.gefsu.OptionalUtils.lift;

public class HandlerMap {
    private final Map<HttpMethod,List<Pair<String,Pair<Object,Method>>>> requestHandlers;

    public static HandlerMap create(final List<Object> controllerObjects) {
        final var parsed = Arrays.stream(HttpMethod.values())
            .collect(Collectors.toMap(
                httpMethod -> httpMethod,
                httpMethod -> findHandlers(httpMethod, controllerObjects)));

        return new HandlerMap(parsed);
    }

    public HttpResponse invokeHandler(
        final HttpMethod requestMethod,
        final String requestResource)
    {
        return findHandler(requestMethod, requestResource)
            .flatMap(pair ->
                lift(() -> (HttpResponse)
                    pair.getRight().invoke(pair.getLeft(), requestResource)))
            .orElseGet(() -> new HttpResponseBuilder()
                .buildSimpleErrorResponse(500));
    }

    @SuppressWarnings("Convert2MethodRef")
    private Optional<Pair<Object,Method>> findHandler(
        HttpMethod requestMethod,
        String requestResource)
    {
        return requestHandlers
            .get(requestMethod)
            .stream()
            .filter(pair -> requestResource.matches(pair.getLeft()))
            .findFirst()
            .map(pair -> pair.getRight());
    }

    private static List<Pair<String, Pair<Object,Method>>> findHandlers(
        HttpMethod httpMethod,
        List<Object> controllerObjects)
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

    private HandlerMap(
        final Map<HttpMethod,List<Pair<String,Pair<Object,Method>>>> parsedHandlers)
    {
        requestHandlers = parsedHandlers;
    }
}
