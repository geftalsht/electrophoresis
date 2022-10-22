package org.gefsu.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class BadRequestResponder {
    public void respond(OutputStream socketOut) {
        try (var writer = new BufferedWriter(
            new OutputStreamWriter(socketOut))) {
            writer.write("HTTP/1.1 400 Bad Request");
        }
        catch (IOException e) {
            System.out.println("Error writing to the socket output stream");
        }
    }
}
