package org.gefsu;

import java.io.IOException;
import java.io.OutputStream;

// Reply with a status code and nothing else
public class SimpleCommand implements Command {

    private final RequestReceiver receiver;
    private final OutputStream socketOut;
    private final int statusCode;

    public SimpleCommand(OutputStream socketOut, int statusCode) {

        this.socketOut = socketOut;
        this.statusCode = statusCode;
        this.receiver = new RequestReceiver();
    }

    @Override
    public void execute() throws IOException {
        receiver.simpleRespond(socketOut, statusCode);
    }

}
