package org.gefsu;

import org.gefsu.http.request.HttpMethod;
import org.gefsu.http.request.HttpRequest;
import java.io.OutputStream;
import java.util.Map;
import java.util.Optional;

public abstract class HttpHandler {

    private static final Map<HttpMethod, HttpHandler> handlerMap;

    static {
        var getHandler = new GetHandler();
        var headHandler = new HeadHandler();
        var notImplementedHandler = ErrorHandler.notImplemented();

        handlerMap = Map.of(
            HttpMethod.GET, getHandler,
            HttpMethod.HEAD, headHandler,
            HttpMethod.POST, notImplementedHandler,
            HttpMethod.PUT, notImplementedHandler,
            HttpMethod.DELETE, notImplementedHandler,
            HttpMethod.CONNECT, notImplementedHandler,
            HttpMethod.OPTIONS, notImplementedHandler,
            HttpMethod.TRACE, notImplementedHandler
        );
    }

    public static Optional<HttpHandler> getHandler(HttpRequest request) {
        return OptionalUtils.lift(() -> handlerMap.get(request.getMethod()));
    }

    public static HttpHandler errorHandler() {
        return ErrorHandler.generic();
    }

    public abstract void handle(OutputStream outputStream);

}
