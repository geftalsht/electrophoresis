package org.gefsu.http;

import java.io.IOException;
import java.io.OutputStream;

public class BadRequestResponder implements RequestResponder {
    @Override
    public void respond(String clientRequest, OutputStream socketOut) {
        try  {
            HttpResponse response = new HttpResponseBuilderImpl()
                .statusCode(400)
                .build();
            socketOut.write(response.toBytes());
        }
        catch (IOException e) {
            System.out.println("Error writing to the socket output stream");
        }
    }
}
