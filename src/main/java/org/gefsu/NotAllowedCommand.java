package org.gefsu;

import java.io.IOException;
import java.io.OutputStream;

public class NotAllowedCommand implements Command {

    private final RequestReceiver receiver;
    private final OutputStream socketOut;

    public NotAllowedCommand(OutputStream socketOut) {
        this.socketOut = socketOut;
        this.receiver = new RequestReceiver();
    }

    @Override
    public void execute() throws IOException {
        receiver.notAllowedRespond(socketOut);
    }

}
