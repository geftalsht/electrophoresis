package org.gefsu;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    @SuppressWarnings("InfiniteLoopStatement")
    public static void start(int port) {

        try (var serverSocket = new ServerSocket(port)) {
            while (true)
                listen(serverSocket);
        }
        catch (IOException e) {
            System.out.println("Fatal I/O Error!");
        }
    }

    private static void listen(ServerSocket serverSocket)
            throws IOException {

        // Create a clientSocket object
        try (var clientSocket = serverSocket.accept();
             var socketIn = clientSocket.getInputStream();
             var socketOut = clientSocket.getOutputStream())
        {
            RequestHandler.handleClient(socketIn, socketOut);
        }
    }
}
