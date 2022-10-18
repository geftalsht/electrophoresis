package org.gefsu;

import java.net.Socket;

public interface IRequestHandler {
    void handleRequest(String request, Socket clientSocket);
}
