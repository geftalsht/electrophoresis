package org.gefsu.server;

import lombok.AllArgsConstructor;
import org.gefsu.http.GetRequest;
import org.gefsu.http.Request;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class RequestHandler implements IRequestHandler {

    private Socket clientSocket;

    @Override
    public void processRequest(String clientRequest) {

        String verb = "";
        Pattern pattern = Pattern.compile("^\\w+");
        Matcher matcher = pattern.matcher(clientRequest);

        // Find an HTTP verb with regex. If no verb found 400.
        if (matcher.find()) {
            verb = matcher.group().toUpperCase();
        }
        else {
            // 400 BAD REQUEST
        }

        // If verb is GET process it as a GET request. If anything else 400.
        if (verb.equals("GET")) {
            Request request = new GetRequest(clientSocket, clientRequest);
            request.process();
        }
        else {
            // 400 BAD REQUEST
        }
    }
}
