package org.gefsu.http;

import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetRequestReceiver extends Receiver {

    public GetRequestReceiver(Socket clientSocket, String clientRequest) {
        super(clientSocket, clientRequest);
    }

    @Override
    public void receive() {

    }

    private String extractResourceNameFromGetRequest(String clientRequest) {

        // Try to extract what resource is requested by GET
        Pattern pattern = Pattern.compile("(?<=^GET\\s)(\\S*)");
        Matcher matcher = pattern.matcher(clientRequest);

        if (matcher.find())
            return (matcher.group().toLowerCase());

        return "";
    }

    private boolean resourceExists(String fileName) {
        return getClass().getResource(fileName) != null;
    }
}
