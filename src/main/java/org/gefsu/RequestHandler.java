package org.gefsu;

import org.gefsu.http.exception.BadRequestException;
import org.gefsu.http.request.HttpMethod;
import org.gefsu.http.request.HttpParser;
import org.gefsu.http.response.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class RequestHandler {

    private static final Map<HttpMethod, Responder> responderMap;

    static {
        var getResponder = new GetResponder();
        var headResponder = new HeadResponder();
        var notAllowedResponder = new NotAllowedResponder();

        responderMap = Map.of(
            HttpMethod.GET, getResponder,
            HttpMethod.HEAD, headResponder,
            HttpMethod.POST, notAllowedResponder,
            HttpMethod.PUT, notAllowedResponder,
            HttpMethod.DELETE, notAllowedResponder,
            HttpMethod.CONNECT, notAllowedResponder,
            HttpMethod.OPTIONS, notAllowedResponder,
            HttpMethod.TRACE, notAllowedResponder
        );
    }

    public static void handleClient(InputStream socketIn, OutputStream socketOut)
        throws IOException {
        try {
            var request = HttpParser.parseRequest(socketIn);
            var responder = responderMap.get(request.getMethod());
            responder.respond(request, socketOut);
        } catch (BadRequestException e) {
            BadRequestResponder.respond(socketOut);
        }
    }
}
