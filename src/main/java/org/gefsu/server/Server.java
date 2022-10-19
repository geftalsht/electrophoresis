package org.gefsu.server;

import org.gefsu.configuration.ServerConfiguration;
import org.jetbrains.annotations.NotNull;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements IServer {

    ServerSocket serverSocket;
    Socket clientSocket;

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void start(@NotNull ServerConfiguration configuration) {

        int port = configuration.getPortNumber();

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            this.listen();
        }
    }

    private void listen() {

        // Create a clientSocket object
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Read String from the clientSocket. Send it to the request handler.
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())))
        {
            IRequestHandler handler = new RequestHandler(clientSocket);
            handler.processRequest(reader.readLine());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Close server and client sockets
    @Override
    public void stop() {
        try {
            serverSocket.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
