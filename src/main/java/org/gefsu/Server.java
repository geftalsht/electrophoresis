package org.gefsu;

import org.gefsu.http.HttpParser;
import org.gefsu.http.HttpRequest;

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
        while (true) {
            try (var client = socket.accept()) {
                handleConnection(client);
            } catch (IOException e) {
                System.out.println("Failed to obtain the client socket!");
            }
        }
    }

    private void handleConnection(Socket client) {

        //  Contains information about the HttpRequest if parsing succeeded, or an empty optional if parsing failed
        final var request = OptionalUtils.lift(client::getInputStream)
            .flatMap(HttpParser::parseRequest);

        // Returns a handler by either locating it in the map or by creating a generic error handler
        final var handler = request
            .flatMap(HttpHandler::getHandler)
            .orElseGet(HttpHandler::genericErrorHandler);

        OptionalUtils.lift(client::getOutputStream)
            .ifPresent(s -> handler.handle(s, request.map(r -> r.getResource())));

    }

}
