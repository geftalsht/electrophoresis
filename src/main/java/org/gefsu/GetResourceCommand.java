package org.gefsu;

import org.gefsu.http.exception.ForbiddenException;
import org.gefsu.http.exception.NotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class GetResourceCommand implements Command {

    private final RequestReceiver receiver;
    private final OutputStream socketOut;
    private final String resourceName;

    public GetResourceCommand(OutputStream socketOut, String resourceName) {
        this.socketOut = socketOut;
        this.resourceName = resourceName;
        this.receiver = new RequestReceiver();
    }

    @Override
    public void execute() throws IOException {
        try {
            receiver.getResource(resourceName, socketOut);
        } catch (NotFoundException e) {
            receiver.simpleRespond(socketOut, 404);
        } catch (ForbiddenException e) {
            receiver.simpleRespond(socketOut, 403);
        }
    }

}
