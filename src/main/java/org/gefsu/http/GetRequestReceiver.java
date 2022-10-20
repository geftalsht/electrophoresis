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
                    try (var in = new BufferedReader(
                        new InputStreamReader(x));
                         var out = new BufferedWriter(
                             new OutputStreamWriter(clientSocket.getOutputStream()))) {
                        out.write("HTTP/1.1 200 OK\r\n");
                        out.write("Content-Type: text/html\r\n");
                        out.write("\r\n");
                        in.transferTo(out);
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }},

                () -> {
                    try (var out = new BufferedWriter(
                        new OutputStreamWriter(clientSocket.getOutputStream()))) {
                        out.write("HTTP/1.1 404 Not Found\r\n");
                        out.write("Content-Type: text/html; charset=utf-8\r\n");
                        out.write("\r\n");
                        out.write(
                            "<html><head>\n" +
                            "<title>404 Not Found</title>\n" +
                            "</head><body>\n" +
                            "<h1>Not Found</h1>\n" +
                            "<p>The requested URL /cock was not found on this server.</p>\n" +
                            "</body></html>"
                        );
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
