package org.gefsu;

import org.gefsu.http.HttpResponseBuilderImpl;
import org.gefsu.http.exception.NotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class RequestReceiver {

    Properties properties = new Properties();

    public void getResource(String resourceName, OutputStream respondTo)
        throws IOException, NotFoundException {

        try (var fis = getClass()
                     .getResourceAsStream("/mimetypes.properties")) {
            properties.load(fis);
        }

        var mimeType = properties.getProperty(determineFileExtension(resourceName));

    }

    public void notAllowedRespond(OutputStream respondTo)
        throws IOException {

        var responseBuilder = new HttpResponseBuilderImpl<String>();
        responseBuilder.setStatusCode(405);

        var response = responseBuilder.build();
        respondTo.write(response.metaToBytes());

    }

    public void badRespond(OutputStream respondTo)
        throws IOException {

        var responseBuilder = new HttpResponseBuilderImpl<String>();
        responseBuilder.setStatusCode(400);

        var response = responseBuilder.build();
        respondTo.write(response.metaToBytes());
    }

    public void notFoundRespond(OutputStream respondTo)
        throws IOException {

        var responseBuilder = new HttpResponseBuilderImpl<String>();
        responseBuilder.setStatusCode(404);

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
