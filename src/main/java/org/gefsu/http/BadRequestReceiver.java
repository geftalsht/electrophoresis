package org.gefsu.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

// Yes, I know about ISP. clientRequest is not a method, but
// a field. I know an argument could be made that it should be
// here, but oh well
public class BadRequestReceiver extends Receiver {

    public BadRequestReceiver(Socket clientSocket, String clientRequest) {
        super(clientSocket, clientRequest);
    }

    @Override
    public void receive() {

        // Write Bad Request to clientSocket
        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(clientSocket.getOutputStream()))))
        {
            writer.write("HTTP/1.1 400 Bad Request\r\n");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
