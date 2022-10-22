package org.gefsu;

import org.gefsu.http.BadRequestResponder;

import java.io.*;
import java.util.regex.Pattern;

public class RequestHandler {

    public void handleClient(InputStream socketIn, OutputStream socketOut) {
        try {
            var clientRequest = readInputStreamToString(socketIn);

            if (!extractHttpVerbFromRequestString(clientRequest).equals("GET")) {
                var responder = new BadRequestResponder();
                responder.respond(socketOut);
            }
            else {
                // FIXME NOT IMPLEMENTED
                System.out.println("Hello, my frens!");
            }

        } catch (IOException e) {
            System.out.println("Error reading the socket input stream");
            throw new RuntimeException(e);
        }
    }

    private String readInputStreamToString(InputStream is)
        throws IOException {

        var result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        for (int len; (len = is.read(buffer)) != -1; ) {
            result.write(buffer, 0, len);
        }
        return result.toString();
    }

    private String extractHttpVerbFromRequestString(String clientRequest) {
        var pattern = Pattern.compile("^\\w+");
        var matcher = pattern.matcher(clientRequest);

        if (matcher.find()) {
            return matcher.group().toUpperCase();
        }
        return "";
    }

}
