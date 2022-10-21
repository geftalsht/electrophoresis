package org.gefsu.http;

import java.io.File;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetRequestReceiver extends Receiver {

    public GetRequestReceiver(Socket clientSocket, String clientRequest) {
        super(clientSocket, clientRequest);
    }

    @Override
    public void receive() {
        // FIXME NOT IMPLEMENTED
    }

    private String extractFileNameFromGetRequest(String clientRequest) {

        // Try to extract what resource is requested by GET
        Pattern pattern = Pattern.compile("(?<=^GET\\s)(\\S*)");
        Matcher matcher = pattern.matcher(clientRequest);

        if (matcher.find())
            return matcher.group().toLowerCase();

        return "";
    }

    private boolean fileExistsAndIsNotADirectory(String fileName)
        throws URISyntaxException {

        var url = getClass()
            .getResource(fileName);

        if (url != null) {
            var file = new File(url.toURI());
            return file.exists() && !file.isDirectory();
        }
        return false;
    }

    private MimeType getMimeTypeFromFileName(String fileName) {
        return getMimeTypeFromExtension(extractExtensionFromFileName(fileName));
    }

    private String extractExtensionFromFileName(String fileName) {

        Pattern pattern = Pattern.compile("\\.\\S+");
        Matcher matcher = pattern.matcher(fileName);

        if (matcher.find())
            return matcher.group().toLowerCase();

        return "";
    }

    private MimeType getMimeTypeFromExtension(String fileExtension) {
        return (
            switch (fileExtension) {
                case "txt" -> MimeType.PLAINTEXT;
                case "html" -> MimeType.HTML;
                case "css" -> MimeType.CSS;
                case "json" -> MimeType.JSON;
                case "jpeg" -> MimeType.JPEG;
                case "png" -> MimeType.PNG;
                default -> MimeType.ARBITRARY;
        });
    }
}
