package org.gefsu.http;

import java.io.*;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

public class GetRequestResponder implements RequestResponder {

    @Override
    public void respond(String clientRequest, OutputStream socketOut) {

        var fileName
            = returnFirstRegexpMatch(clientRequest, "(?<=^GET\\s)(\\S*)");
        var mimeType
            = determineMimeTypeFromExtension(
            returnFirstRegexpMatch(fileName, "(?<=\\.)(\\S*)"));
        var fileUrl = getClass().getResource(fileName);

        // If path to resource is completely invalid, throw a 404
        if (fileUrl == null) {
            try {
                HttpResponse response = new HttpResponseBuilderImpl()
                    .statusCode(404)
                    .build();
                socketOut.write(response.toBytes());
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            var file = new File(fileUrl.toURI());

            // If requested resource is a directory, throw a 403
            if (file.isDirectory()) {
                    HttpResponse response = new HttpResponseBuilderImpl()
                        .statusCode(403)
                        .build();
                    socketOut.write(response.toBytes());
                    return;
                }

            // If everything seems alright, send it with a 200
            try (var fis = new FileInputStream(file)) {
                var body = readFileInputStream(fis);
                HttpResponse response = new HttpResponseBuilderImpl()
                    .statusCode(200)
                    .mimeType(mimeType)
                    .body(body)
                    .build();
                socketOut.write(response.toBytes());
            }

        } catch (URISyntaxException | IOException e) {
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

    private byte[] readFileInputStream(FileInputStream fis)
        throws IOException {

        var buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];

        while ((nRead = fis.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();
    }

}
