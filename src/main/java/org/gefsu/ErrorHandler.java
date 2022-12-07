package org.gefsu;

import org.gefsu.http.HttpResponseMetaBuilder;
import org.gefsu.http.HttpRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

public class ErrorHandler extends HttpHandler {
    private final int statusCode;

    private ErrorHandler(int statusCode) {
        this.statusCode = statusCode;
    }

    // Static factories for common error types
    public static ErrorHandler generic() {
        return new ErrorHandler(500);
    }

    public static ErrorHandler notImplemented() {
        return new ErrorHandler(501);
    }

    public static ErrorHandler notFound() {
        return new ErrorHandler(404);
    }

    public static ErrorHandler badRequest() {
        return new ErrorHandler(400);
    }

    @Override
    public void handle(OutputStream outputStream, Optional<HttpRequest> request) {
        var response = new HttpResponseMetaBuilder()
            .setStatusCode(statusCode)
            .build();

        try {
            outputStream.write(response.metaToBytes());
        } catch (IOException e) {
            System.out.println("Error writing to the OutputStream");
        }
    }
}
