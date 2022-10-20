package org.gefsu.http;

import java.net.Socket;

public abstract class Receiver {
    protected final Socket clientSocket;
    protected final String clientRequest;

    public Receiver(Socket clientSocket, String clientRequest) {
        this.clientSocket = clientSocket;
        this.clientRequest = clientRequest;
    }

    public abstract void receive();

}
