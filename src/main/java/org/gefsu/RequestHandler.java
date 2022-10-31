package org.gefsu;

import org.gefsu.http.exception.BadRequestException;
import org.gefsu.http.request.HttpMethod;
import org.gefsu.http.request.HttpParser;
import org.gefsu.http.request.HttpRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RequestHandler {

    private final InputStream socketIn;
    private final OutputStream socketOut;
    Command command;

    public RequestHandler(InputStream socketIn, OutputStream socketOut) {
        this.socketIn = socketIn;
        this.socketOut = socketOut;
    }

    public void handleClient() throws IOException {

        try {
            command = createCommand(HttpParser.parseRequest(socketIn));
        } catch (BadRequestException e) {
            command = new BadCommand(socketOut);
        }
        command.execute();
        
    }

    private Command createCommand(HttpRequest request) {
        var method = request.getMethod();

        if (method == HttpMethod.GET)
            return new GetResourceCommand(socketOut, request.getResource());

        return new NotAllowedCommand(socketOut);
    }

}
