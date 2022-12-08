package org.gefsu;

import java.io.IOException;
import java.net.ServerSocket;
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
                new SocketController(client).torture();
            } catch (IOException e) {
                System.out.println("Failed to obtain the client socket!");
            }
        }
    }
}
