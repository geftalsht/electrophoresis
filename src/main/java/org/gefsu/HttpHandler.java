package org.gefsu;

import org.gefsu.http.HttpMethod;
import org.gefsu.http.HttpRequest;
import java.io.OutputStream;
import java.util.Map;
import java.util.Optional;

import static org.gefsu.OptionalUtils.lift;

public abstract class HttpHandler {

    private static final Map<HttpMethod, HttpHandler> handlerMap;

    static {
        var getHandler = new GetHandler();
        var notImplementedHandler = ErrorHandler.notImplemented();

        handlerMap = Map.of(
            HttpMethod.GET, getHandler,
            HttpMethod.HEAD, notImplementedHandler,
            HttpMethod.POST, notImplementedHandler,
            HttpMethod.PUT, notImplementedHandler,
            HttpMethod.DELETE, notImplementedHandler,
            HttpMethod.CONNECT, notImplementedHandler,
            HttpMethod.OPTIONS, notImplementedHandler,
            HttpMethod.TRACE, notImplementedHandler
        );
    }

    public static Optional<HttpHandler> getHandler(HttpRequest request) {
        return lift(() -> handlerMap.get(request.getMethod()));
    }

    public static HttpHandler genericErrorHandler() {
        return ErrorHandler.generic();
    }

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public abstract void handle(OutputStream outputStream, Optional<HttpRequest> request);

}
