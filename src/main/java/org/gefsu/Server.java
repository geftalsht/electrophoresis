package org.gefsu;

import org.gefsu.http.HttpParser;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

import static org.gefsu.OptionalUtils.lift;

class Server {
    private final ServerSocket socket;

    private Server(int port) throws IOException {
        socket = new ServerSocket(port);
    }

    public static Optional<Server> makeServer(int port) {
        return lift(() -> new Server(port));
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

    // TODO Replace this with the invocation of SocketController pipeline
    private void handleConnection(Socket client) {
        //  Contains information about the HttpRequest if parsing succeeded, or an empty optional if parsing failed
        final var request = lift(client::getInputStream)
            .flatMap(HttpParser::parseRequest);

        // Returns a handler by either locating it in the map or by creating a generic error handler
        final var handler = request
            .flatMap(HttpHandler::getHandler)
            .orElseGet(HttpHandler::genericErrorHandler);

        lift(client::getOutputStream)
            .ifPresent(s -> handler.handle(s, request));

        try {
            client.close();
        } catch (IOException e) {
            System.out.println("Error closing the client socket.");
        }
    }
}
