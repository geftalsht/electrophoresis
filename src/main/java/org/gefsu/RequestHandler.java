package org.gefsu;

import org.gefsu.http.BadRequestException;
import org.gefsu.http.ScuffedHttpParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RequestHandler {

    public static void handleClient(InputStream socketIn, OutputStream socketOut)
        throws IOException {

        try {
            var request = ScuffedHttpParser.parseRequest(socketIn);
        } catch (BadRequestException e) {
            // Send the 400, soldier!
        }

    }
}
