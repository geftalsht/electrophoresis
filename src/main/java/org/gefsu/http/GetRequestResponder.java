package org.gefsu.http;

import java.io.*;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

public class GetRequestResponder implements RequestResponder {

    @Override
    public void respond(String clientRequest, OutputStream socketOut) {

        var fileName = extractFileNameFromRequest(clientRequest);

        try (var writer = new BufferedWriter(
            new OutputStreamWriter(socketOut))) {

            if (!resourceExistsAndIsNotDirectory(fileName)) {
                HttpResponse<String> response = new HttpResponseBuilderImpl<String>()
                    .statusCode(404)
                    .build();
                writer.write(response.toString());
            }
            else {
                System.out.println("NOT IMPLEMENTED");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String extractFileNameFromRequest(String clientRequest) {
        var pattern = Pattern.compile("(?<=^GET\\s)(\\S*)");
        var matcher = pattern.matcher(clientRequest);

        if (matcher.find())
            return matcher.group();

        return "";
    }

    public boolean resourceExistsAndIsNotDirectory(String fileName) {

        var url = getClass().getResource(fileName);

        if (url == null)
            return false;

        try {
            var file = new File(url.toURI());
            if (file.isDirectory())
                return false;
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

}
