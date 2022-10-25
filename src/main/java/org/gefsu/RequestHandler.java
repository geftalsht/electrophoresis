package org.gefsu;

import org.gefsu.http.BadRequestResponder;
import org.gefsu.http.GetRequestResponder;
import org.gefsu.http.RequestResponder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

public class RequestHandler {

    public void handleClient(InputStream socketIn, OutputStream socketOut) {
        try {

            RequestResponder responder;
            var clientRequest = readInputStreamToString(socketIn);

            if (!extractHttpVerbFromRequestString(clientRequest).equals("GET"))
                responder = new BadRequestResponder();
            else
                responder = new GetRequestResponder();

            responder.respond(clientRequest, socketOut);

        } catch (IOException e) {
            System.out.println("Error reading the socket input stream");
            throw new RuntimeException(e);
        }
    }

    private String readInputStreamToString(InputStream is)
        throws IOException {

        var result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        while (is.available() != 0) {
            int len = is.read(buffer);
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
