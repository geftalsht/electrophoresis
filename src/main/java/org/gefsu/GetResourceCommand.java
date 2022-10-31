package org.gefsu;

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
    public void execute() {
        receiver.getResource(resourceName, socketOut);
    }

}
