package org.gefsu;

import org.gefsu.http.GetRequestReceiver;
import org.gefsu.http.Receiver;
import org.gefsu.http.BadRequestReceiver;
import java.net.Socket;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHandler {

    public void processRequest(Socket clientSocket, String clientRequest) {

        Receiver receiver;
        Optional<String> httpVerb = extractHttpVerb(clientRequest);

        // If httpVerb is GET process it as a GET request.
        if (httpVerb.isEmpty() || !httpVerb.get().equals("GET"))
            receiver = new BadRequestReceiver(clientSocket, clientRequest);
        else
            // NOT IMPLEMENTED!
            receiver = new GetRequestReceiver(clientSocket, clientRequest);

        receiver.receive();
    }

    private Optional<String> extractHttpVerb(String clientRequest) {

        Pattern pattern = Pattern.compile("^\\w+");
        Matcher matcher = pattern.matcher(clientRequest);

        if (matcher.find()) {
            return Optional.of((matcher.group().toUpperCase()));
        }

        return Optional.empty();
    }
}
