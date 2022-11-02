package org.gefsu;

import org.gefsu.http.HttpResponseMetaBuilder;
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
    public void handle(OutputStream outputStream) {
        // Today I will write the error to the outputStream
        // Clueless
        var response = new HttpResponseMetaBuilder()
            .setStatusCode(statusCode)
            .build();

        // FIXME How to process this exception?
        outputStream.write(response.metaToBytes());
    }
}
