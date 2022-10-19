package org.gefsu.server;

import lombok.AllArgsConstructor;
import org.gefsu.http.GetReceiver;
import org.gefsu.http.Receiver;
import org.gefsu.http.BadRequestReceiver;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class RequestHandler implements IRequestHandler {

    private Socket clientSocket;

    // Could be better
    @Override
    public void processRequest(String clientRequest) {
        Receiver receiver;
        String httpVerb = extractHttpVerb(clientRequest);

        // If httpVerb is GET process it as a GET request.
        if ((httpVerb != null) && httpVerb.equals("GET")) {
            receiver = new GetReceiver(clientSocket, clientRequest);
            receiver.receive();
        }
        // If anything else (including null) 400.
        else {
            receiver = new BadRequestReceiver(clientSocket, clientRequest);
            receiver.receive();
        }
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
