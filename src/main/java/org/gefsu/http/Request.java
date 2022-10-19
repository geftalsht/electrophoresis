package org.gefsu.http;

import java.net.Socket;

public abstract class Request {
    protected Socket clientSocket;
    protected String clientRequest;

    public Request(Socket clientSocket, String clientRequest) {
        this.clientSocket = clientSocket;
        this.clientRequest = clientRequest;
    }

    public abstract void process();

}
