package org.gefsu;

import org.gefsu.http.exception.BadRequestException;
import org.gefsu.http.request.HttpMethod;
import org.gefsu.http.request.HttpParser;
import org.gefsu.http.request.HttpRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.Optional;

public class Server {
    public static void main(String[] args) {
        Arrays.stream(args)
            .findFirst()
            .flatMap(Server::safeParseInt)
            .filter(Server::portIsValid)
            .flatMap(Server::tryServerSocket)
            .ifPresentOrElse(
                Server::listen,
                () -> System.out.println("Server failed to start."));
    }

    private static Optional<Integer> safeParseInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static boolean portIsValid(int port) {
        return port >= 0 && port <= 65535;
    }

    private static Optional<ServerSocket> tryServerSocket(int port) {
        try {
            return Optional.of(new ServerSocket(port));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @SuppressWarnings("InfiniteRecursion")
    private static void listen(ServerSocket serverSocket) {
        // Create a clientSocket object
        try (var clientSocket = serverSocket.accept();
             var socketIn = clientSocket.getInputStream();
             var socketOut = clientSocket.getOutputStream())
        {
            handleClient(socketIn, socketOut);
        } catch (IOException e) {
            System.out.println("Failed to obtain the client socket!");
        } finally {
            listen(serverSocket);
        }
    }

    private static void handleClient(InputStream socketIn, OutputStream socketOut)
        throws IOException {

        Command command;

        try {
            command = createCommand(HttpParser.parseRequest(socketIn), socketOut);
        } catch (BadRequestException e) {
            command = new SimpleCommand(socketOut, 400);
        }

        command.execute();
    }

    private static Command createCommand(HttpRequest request, OutputStream socketOut) {

        if (request.getMethod() == HttpMethod.GET)
            return new GetResourceCommand(socketOut, request.getResource());

        return new SimpleCommand(socketOut, 405);
    }

}
