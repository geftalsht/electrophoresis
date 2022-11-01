package org.gefsu;

import org.gefsu.http.request.HttpParser;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

import static org.gefsu.OptionalUtils.ifPresent;

class Server {

    private final ServerSocket socket;

    private Server(int port) throws IOException {
        socket = new ServerSocket(port);
    }

    public static Optional<Server> makeServer(int port) {
        return OptionalUtils.lift(() -> new Server(port));
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void listen() {
        // Create a clientSocket object
        while (true) {
            try (var client = socket.accept()) {
                handleConnection(client);
            } catch (IOException e) {
                System.out.println("Failed to obtain the client socket!");
            }
        }
    }

    private void handleConnection(Socket client) {
         //try (var client = socket.accept();
        //                 var socketIn = client.getInputStream();
        //                 var socketOut = client.getOutputStream()) {
        //                Main.handleClient(socketIn, socketOut);
        var handler = OptionalUtils.lift(client::getInputStream)
            .flatMap(HttpParser::parseRequest)
            .map(r -> HttpHandler.getHandler(r))
            .or(() -> Optional.ofNullable(HttpHandler.errorHandler()));

        ifPresent(handler, OptionalUtils.lift(client::getOutputStream), HttpHandler::handle);

    }

}
