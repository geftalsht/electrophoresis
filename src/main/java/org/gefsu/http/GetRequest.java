package org.gefsu.http;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetRequest extends Request {

    String resource;

    public GetRequest(Socket clientSocket, String clientRequest) {
        super(clientSocket, clientRequest);
    }

    @Override
    public void process() {

        Pattern pattern = Pattern.compile("(?<=^GET\\s/)(\\S*)");
        Matcher matcher = pattern.matcher(clientRequest);

        if (matcher.find()) {
            resource = matcher.group().toLowerCase();
        }
        else {
            // 400 BAD REQUEST
        }

        File file = new File(resource);

        try (PrintWriter writer = new PrintWriter(
                new BufferedWriter(
                        new OutputStreamWriter(clientSocket.getOutputStream()))))
        {
            if (file.exists()) {
                writer.write("HTTP/1.1 200 OK\r\n");
                writer.write("Server: Electrophoresis 0.1.0\r\n");
                writer.write("Content-Type: text/html; charset=UTF-8\r\n");
                writer.write("Content length: " + file.toString().length() + "\r\n");
                writer.write("\r\n");
                writer.write(file.toString());
            }
            else {
                System.out.println("Bruh");
                writer.write("HTTP/1.1 404 Not Found\r\n");
                writer.write("Server: Electrophoresis 0.1.0\r\n");
                writer.write("Content-Type: text/html; charset=UTF-8\r\n");
                writer.write("\r\n");
                writer.write("<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Bruh Moment</title>\n" +
                        "</head>\n" +
                        "\n" +
                        "<body>\n" +
                        "    <p>Bruh</p>\n" +
                        "</body>\n" +
                        "</html>");
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
