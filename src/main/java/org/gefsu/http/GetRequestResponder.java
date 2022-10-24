package org.gefsu.http;

import java.io.*;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

public class GetRequestResponder implements RequestResponder {

    @Override
    public void respond(String clientRequest, OutputStream socketOut) {

        var fileName
            = returnFirstRegexpMatch(clientRequest, "(?<=^GET\\s)(\\S*)");

        try (var writer = new BufferedWriter(
            new OutputStreamWriter(socketOut))) {

            if (!resourceExistsAndIsNotDirectory(fileName)) {
                HttpResponse<String> response = new HttpResponseBuilderImpl<String>()
                    .statusCode(404)
                    .build();
                writer.write(response.toString());
            }
            else {
                var mimeType
                    = determineMimeTypeFromExtension(
                        returnFirstRegexpMatch(fileName, "(?<=\\.)(\\S*)"));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String returnFirstRegexpMatch(String input, String regex) {
        var pattern = Pattern.compile(regex);
        var matcher = pattern.matcher(input);

        if (matcher.find())
            return matcher.group();

        return "";
    }

    private boolean resourceExistsAndIsNotDirectory(String fileName) {

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

    private MimeType determineMimeTypeFromExtension(String fileExtension) {
        return
            switch (fileExtension) {
                case "txt" -> MimeType.PLAINTEXT;
                case "htm", "html" -> MimeType.HTML;
                case "css" -> MimeType.CSS;
                case "js" -> MimeType.JS;
                case "json" -> MimeType.JSON;
                case "jpg", "jpeg" -> MimeType.JPEG;
                case "png" -> MimeType.PNG;
                default -> MimeType.BINARY;
            };
    }

}
