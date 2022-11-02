package org.gefsu;

import org.gefsu.http.HttpResponseMetaBuilder;
import org.gefsu.http.HttpRequest;
import java.io.IOException;
import java.io.OutputStream;

public class ErrorHandler extends HttpHandler {

    private final int statusCode;

    private ErrorHandler(int statusCode) {
        this.statusCode = statusCode;
    }

    public static ErrorHandler generic() {
        return new ErrorHandler(500);
    }

    public static ErrorHandler notImplemented() {
        return new ErrorHandler(501);
    }

    @Override
    public void handle(OutputStream outputStream, HttpRequest request) {
        var response = new HttpResponseMetaBuilder()
            .setStatusCode(statusCode)
            .build();

        // TODO Any other ideas?
        try {
            outputStream.write(response.metaToBytes());
        } catch (IOException e) {
            System.out.println("Error writing to the OutputStream");
        }

    }
}
