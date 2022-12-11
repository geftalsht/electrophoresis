package org.gefsu;

import org.gefsu.http.HttpResponseBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static org.gefsu.http.HttpParser.parseRequest;

public class SocketController {
    private final InputStream clientInput;
    private final OutputStream clientOutput;
    private final RequestHandlerDispatcher handlerDispatcher;

    public SocketController(
        Socket clientSocket, RequestHandlerDispatcher handlerDispatcher
    ) throws IOException {
        this.handlerDispatcher = handlerDispatcher;
        clientInput = clientSocket.getInputStream();
        clientOutput = clientSocket.getOutputStream();
    }

    @SuppressWarnings("Convert2MethodRef")
    public void processRequests() {
        final var response = parseRequest(clientInput)
            .map(request -> handlerDispatcher
                .handleRequest(request))
            .orElseGet(() -> new HttpResponseBuilder()
                .buildSimpleErrorResponse(400));
    }
}
