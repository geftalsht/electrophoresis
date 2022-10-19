package org.gefsu;

import org.gefsu.http.GetRequestReceiver;
import org.gefsu.http.Receiver;
import org.gefsu.http.BadRequestReceiver;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestHandler {

    public void processRequest(Socket clientSocket, String clientRequest) {

        Receiver receiver;
        String httpVerb = extractHttpVerb(clientRequest);

        // If httpVerb is GET process it as a GET request.
        if ((httpVerb != null) && httpVerb.equals("GET"))
            receiver = new GetRequestReceiver(clientSocket, clientRequest);
        // If anything else (including null) 400.
        else
            receiver = new BadRequestReceiver(clientSocket, clientRequest);

        receiver.receive();
    }

    private String extractHttpVerb(String clientRequest) {

        Pattern pattern = Pattern.compile("^\\w+");
        Matcher matcher = pattern.matcher(clientRequest);

        // Find an HTTP verb with regex. If no verb found 400.
        if (matcher.find()) {
            return(matcher.group().toUpperCase());
        }
        return null;
    }
}
