package org.gefsu;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Optional;

import static org.gefsu.OptionalUtils.lift;
import static org.gefsu.http.HttpParser.parseRequest;

@SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "Convert2MethodRef"})
public class SocketController {
    private final Optional<InputStream > clientInput;
    private final Optional<OutputStream> clientOutput;
    private final RequestHandlerDispatcher handlerDispatcher;

    public SocketController(
        Socket clientSocket,
        RequestHandlerDispatcher handlerDispatcher)
    {
        this.handlerDispatcher = handlerDispatcher;
        clientInput = lift(() -> clientSocket.getInputStream());
        clientOutput = lift(() -> clientSocket.getOutputStream());
    }

    // TODO Finish writing the pipeline
    public void torture() {
        final var balls = clientInput
            .flatMap(clientInput -> parseRequest(clientInput))
            .flatMap(request -> handlerDispatcher.handleRequest(request));
    }
}
