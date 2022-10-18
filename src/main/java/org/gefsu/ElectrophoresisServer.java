package org.gefsu;

import org.jetbrains.annotations.NotNull;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ElectrophoresisServer implements IElectrophoresisServer {

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

        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())))
        {
            // TODO Pass request information to the handler
            IRequestHandler handler = new RequestHandler();
            handler.handleRequest(reader.readLine(), clientSocket);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
