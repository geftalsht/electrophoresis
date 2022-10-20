package org.gefsu.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class BadRequestReceiver extends Receiver {

    public BadRequestReceiver(Socket clientSocket, String clientRequest) {
        super(clientSocket, clientRequest);
    }

    @Override
    public void receive() {
        // Write Bad Request to clientSocket
        try (var writer = new BufferedWriter(
                new OutputStreamWriter(clientSocket.getOutputStream())))
        {
            writer.write("HTTP/1.1 400 Bad Request\r\n");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
