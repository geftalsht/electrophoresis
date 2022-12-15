package org.gefsu;

import org.gefsu.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HandlerMap {
    private final Map<HttpMethod,List<Pair<String,Pair<?,Method>>>> requestHandlers;

    public static HandlerMap create(final List<?> controllerObjects) {
        final var parsed = Arrays.stream(HttpMethod.values())
            .collect(Collectors.toMap(
                httpMethod -> httpMethod,
                httpMethod -> findHandlers(httpMethod, controllerObjects)));

        return new HandlerMap(parsed);
    }
//        final var parsedd = controllerObjects
//            .stream()
//            .flatMap(controller -> Arrays.stream(controller
//                    .getClass()
//                    .getMethods())
//                .filter(method -> method.isAnnotationPresent(HttpRequestMapping.class))
//                .map(method -> Pair.of(controller, method)))
//            .map(methodPair -> Pair.of(
//                methodPair
//                    .getRight()
//                    .getAnnotation(HttpRequestMapping.class)
//                    .url(),
//                methodPair))
//            .collect(Collectors.toMap(
//                urlpair -> urlpair
//                    .getRight()
//                    .getRight()
//                    .getAnnotation(HttpRequestMapping.class)
//                    .method(),
//                urlpair -> urlpair));
    private static List<Pair<String, Pair<?,Method>>> findHandlers(
        HttpMethod httpMethod, List<?> controllerObjects)
    {
        final List<Pair<String, Pair<?,Method>>> cock = controllerObjects
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

        return cock;
    }

    private HandlerMap(final Map<HttpMethod,List<Pair<String,Pair<?,Method>>>> parsedHandlers) {
        requestHandlers = parsedHandlers;
    }
}
