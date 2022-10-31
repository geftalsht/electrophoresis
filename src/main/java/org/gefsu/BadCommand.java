package org.gefsu;

import java.io.OutputStream;

public class BadCommand implements Command {

    private final RequestReceiver receiver;
    private final OutputStream socketOut;

    public BadCommand(OutputStream socketOut) {
        this.socketOut = socketOut;
        this.receiver = new RequestReceiver();
    }

    @Override
    public void execute() {
        receiver.badRespond(socketOut);
    }

}
