package org.gefsu;

import org.gefsu.http.request.HttpMethod;
import java.io.OutputStream;
import java.util.Optional;

public class HttpHandler {

    public static Optional<HttpHandler> getHandler(HttpMethod method) {
        switch (method)
    }

    public static HttpHandler errorHandler() {
        return null;
    }

    public void handle(OutputStream outputStream) {

    }
}
