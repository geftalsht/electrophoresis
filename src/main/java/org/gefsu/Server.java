package org.gefsu;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Optional;

import static org.gefsu.util.OptionalUtils.lift;

class Server {
    private final ServerSocket socket;
    private final RequestHandlerDispatcher handlerDispatcher;

    private Server(int port, HandlerMap requestHandlers)
        throws IOException
    {
        socket = new ServerSocket(port);
        handlerDispatcher = new RequestHandlerDispatcher(requestHandlers);
    }

    public static Optional<Server> makeServer(int port, HandlerMap requestHandlers)
    {
        return lift(() -> new Server(port, requestHandlers));
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void listen() {
        while (true) {
            try (var client = socket.accept()) {
                new SocketController(client, handlerDispatcher).processRequests();
            } catch (IOException e) {
                System.out.println("Failed to obtain the client socket!");
            }
        }
    }
}
