package org.gefsu;

import org.gefsu.http.HttpParser;
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

        // FIXME: If the request is not present, for example, due to a parsing error, nothing will happen
        ifPresent(OptionalUtils.lift(client::getOutputStream), request, handler::handle);
    }

}
