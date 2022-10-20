package org.gefsu.http;

import java.io.*;
import java.net.Socket;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetRequestReceiver extends Receiver {

    public GetRequestReceiver(Socket clientSocket, String clientRequest) {
        super(clientSocket, clientRequest);
    }

    @Override
    public void receive() {

        // Try to get the InputStream of a requested resource.
        // Write 200 OK and the content of that stream to the socket.
        // If Stream is null write 404 to the socket.

        getInputStreamOfResource(
            extractResourceNameFromGetRequest(clientRequest))
            .ifPresentOrElse(
                x -> {
                    try (BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(x));
                         BufferedWriter bufferedWriter = new BufferedWriter(
                             new OutputStreamWriter(clientSocket.getOutputStream()))) {
                        bufferedWriter.write("HTTP/1.1 200 OK\r\n");
                        bufferedWriter.write("Content-Type: text/html\r\n");
                        bufferedWriter.write("\r\n");
                        bufferedReader.transferTo(bufferedWriter);
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    },

                () -> {
                    try (BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(clientSocket.getOutputStream()))) {
                        bufferedWriter.write("HTTP/1.1 404 Not Found\r\n");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private String extractResourceNameFromGetRequest(String clientRequest) {

        // Try to extract what resource is requested by GET
        Pattern pattern = Pattern.compile("(?<=^GET\\s)(\\S*)");
        Matcher matcher = pattern.matcher(clientRequest);

        if (matcher.find())
            return (matcher.group().toLowerCase());

        return "";
    }

    private Optional<InputStream> getInputStreamOfResource(String fileName) {
        return Optional.ofNullable((getClass().getResourceAsStream(fileName)));
    }
}
