package org.gefsu;

import org.gefsu.http.HttpRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class HandlerDispatcher implements InvocationHandler {
    @SuppressWarnings("Convert2MethodRef")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable
    {
        final var request = Arrays.stream(args)
            .findFirst()
            .filter(arg -> arg instanceof HttpRequest)
            .map(arg -> (HttpRequest) arg);
        final var requestResource = request
            .map(req -> req.getResource());
        final var requestMethod = request
            .map(req -> req.getMethod());
        return null;
    }
}
