package org.gefsu;

import org.gefsu.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler {

    private static final Map<HttpMethod, Responder> responderMap;

    static {
        Map<HttpMethod, Responder> map = new HashMap<>();
        map.put(HttpMethod.GET, )
    }

    public static void handleClient(InputStream socketIn, OutputStream socketOut)
        throws IOException {

        try {

            var request = HttpParser.parseRequest(socketIn);
            var responder = findResponder(request.getMethod().toString());

        } catch (BadRequestException e) {
            BadRequestResponder.respond(socketOut);
        }

    }
}
