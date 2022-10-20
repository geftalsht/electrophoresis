package org.gefsu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    @SuppressWarnings("InfiniteLoopStatement")
    public void start(int port) {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true)
                listen(serverSocket);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void listen(ServerSocket serverSocket)
            throws IOException {

        // Create a clientSocket object
        try (Socket clientSocket = serverSocket.accept();
             BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())))
        {
            RequestHandler handler = new RequestHandler();
            handler.processRequest(clientSocket, reader.readLine());
        }
    }
}
