package org.gefsu.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class BadRequestResponder {
    public void respond(OutputStream socketOut) {
        try (var writer = new BufferedWriter(
            new OutputStreamWriter(socketOut))) {
            HttpResponse<String> response = new HttpResponseBuilderImpl<String>()
                .statusCode(400)
                .build();
            writer.write(response.toString());
        }
        catch (IOException e) {
            System.out.println("Error writing to the socket output stream");
        }
    }
}
