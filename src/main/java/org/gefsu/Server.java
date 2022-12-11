package org.gefsu;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Optional;

import static org.gefsu.OptionalUtils.lift;

class Server {
    private final ServerSocket socket;
    private final RequestHandlerDispatcher handlerDispatcher;

    private Server(int port, ServerSettings settings) throws IOException {
        socket = new ServerSocket(port);
        ScuffedController controller = new ScuffedController(settings);
        handlerDispatcher = new RequestHandlerDispatcher(controller);
    }

    public static Optional<Server> makeServer(int port, ServerSettings settings) {
        return lift(() -> new Server(port, settings));
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void listen() {
        while (true) {
            try (var client = socket.accept()) {
                new SocketController(client, handlerDispatcher).torture();
            } catch (IOException e) {
                System.out.println("Failed to obtain the client socket!");
            }
        }
    }
}
