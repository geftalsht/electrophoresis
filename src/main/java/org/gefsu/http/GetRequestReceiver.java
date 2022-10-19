package org.gefsu.http;

import com.google.common.io.Resources;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetRequestReceiver extends Receiver {

    public GetRequestReceiver(Socket clientSocket, String clientRequest) {
        super(clientSocket, clientRequest);
    }

    @Override
    public void receive() {

        String fileName = extractFileName(clientRequest);

        if (fileName == null) {
            // 404 NOT FOUND
            return;
        }

        // Read requested resource as a String
        URL url = Resources.getResource(fileName);

        try {
            String content = Resources.toString(url, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Write the String to the Socket
        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(clientSocket.getOutputStream()))))
        {
            writer.write("HTTP/1.1 200 OK\r\n");
            writer.write("Content-Type: text/html; charset=UTF-8\r\n");
            writer.write("Content length: " + content.toString().length() + "\r\n");
            writer.write("\r\n");
            writer.write(file.toString());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String extractFileName(String clientRequest) {

        // Try to extract what resource is requested by GET
        Pattern pattern = Pattern.compile("(?<=^GET\\s/)(\\S*)");
        Matcher matcher = pattern.matcher(clientRequest);

        if (matcher.find()) {
            return (matcher.group().toLowerCase());
        }

        return null;
    }
}
