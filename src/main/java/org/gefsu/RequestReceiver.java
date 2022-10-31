package org.gefsu;

import org.gefsu.http.HttpResponseMetaBuilder;
import org.gefsu.http.exception.ForbiddenException;
import org.gefsu.http.exception.NotFoundException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class RequestReceiver {

    final Properties properties = new Properties();

    public void getResource(String resourceName, OutputStream respondTo)
        throws IOException, NotFoundException, ForbiddenException {

        try (var fis = getClass()
                     .getResourceAsStream("/mimetypes.properties")) {
            properties.load(fis);
        }

        var mimeType = properties.getProperty(determineFileExtension(resourceName));
        var fileUrl = getClass().getResource("/html" + resourceName);

        if (fileUrl == null)
            throw new NotFoundException();

        try {
            var file = new File(fileUrl.toURI());

            if (file.isDirectory())
                throw new ForbiddenException();

            try (var fis = new FileInputStream(file)) {

                var responseBuilder = new HttpResponseMetaBuilder();
                responseBuilder.setStatusCode(200);
                responseBuilder.setMimeType(mimeType);
                var response = responseBuilder.build();

                respondTo.write(response.metaToBytes());
                respondTo.write("\r\n".getBytes(StandardCharsets.UTF_8));
                while (fis.available() > 0)
                    respondTo.write(fis.read());
            }

        } catch (URISyntaxException e) {
            throw new NotFoundException();
        }

    }

    public void notAllowedRespond(OutputStream respondTo)
        throws IOException {

        var responseBuilder = new HttpResponseMetaBuilder();
        responseBuilder.setStatusCode(405);

        var response = responseBuilder.build();
        respondTo.write(response.metaToBytes());
    }

    public void badRespond(OutputStream respondTo)
        throws IOException {

        var responseBuilder = new HttpResponseMetaBuilder();
        responseBuilder.setStatusCode(400);

        var response = responseBuilder.build();
        respondTo.write(response.metaToBytes());
    }

    public void notFoundRespond(OutputStream respondTo)
        throws IOException {

        var responseBuilder = new HttpResponseMetaBuilder();
        responseBuilder.setStatusCode(404);

        var response = responseBuilder.build();
        respondTo.write(response.metaToBytes());
    }

    public void forbiddenRespond(OutputStream respondTo)
        throws IOException {

        var responseBuilder = new HttpResponseMetaBuilder();
        responseBuilder.setStatusCode(403);

        var response = responseBuilder.build();
        respondTo.write(response.metaToBytes());
    }

    private String determineFileExtension(String fileName) {

        var dot = fileName.lastIndexOf('.');

        if (dot != -1)
            return fileName.substring(dot+1);

        return "binary";
    }

}
