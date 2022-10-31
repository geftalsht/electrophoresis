package org.gefsu;

import java.io.OutputStream;

public class NotAllowedCommand implements Command {

    private final RequestReceiver receiver;
    private final OutputStream socketOut;

    public NotAllowedCommand(OutputStream socketOut) {
        this.socketOut = socketOut;
        this.receiver = new RequestReceiver();
    }

    @Override
    public void execute() {
        receiver.notAllowedRespond(socketOut);
    }

}
