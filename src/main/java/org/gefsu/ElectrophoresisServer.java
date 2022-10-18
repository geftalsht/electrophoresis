package org.gefsu;

import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ElectrophoresisServer {

    int port;

    public void start(@NotNull ServerConfiguration configuration) {

        port = configuration.getPortNumber();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Hello, my frens!");
            Socket socket = serverSocket.accept();
        }
        catch (IOException e) {
            System.out.println("IO Error while opening the socket.");
        }
        catch (IllegalArgumentException e) {
            System.out.println("Invalid port number.");
        }
    }

}
