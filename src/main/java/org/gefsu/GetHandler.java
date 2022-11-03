package org.gefsu;

import org.gefsu.http.HttpRequest;
import java.io.OutputStream;
import java.util.Optional;
import java.util.Properties;

public class GetHandler extends HttpHandler {

    // FIXME Implementation not finished
    @Override
    public void handle(OutputStream outputStream, Optional<String> uri) {

        Properties properties = new Properties();

        try (var fis = getClass().getResourceAsStream("/mimetypes.properties")) {
           properties.load(fis);
        }

        var mimeType = properties.getProperty(determineFileExtension(resourceName));

        // Can return null if resource is not found, can throw a NPE if resourceName is null
        var fileUrl = getClass().getResource("/html" + resourceName);
    }

    private String determineFileExtension(String fileName) {
        var dot = fileName.lastIndexOf('.');
        if (dot != -1)
            return fileName.substring(dot+1);
        return "binary";
    }

}
