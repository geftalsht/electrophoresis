package org.gefsu.http;

import java.net.Socket;

public abstract class Receiver {
    protected Socket clientSocket;
    protected String clientRequest;

    public Receiver(Socket clientSocket, String clientRequest) {
        this.clientSocket = clientSocket;
        this.clientRequest = clientRequest;
    }

    public abstract void receive();

}
