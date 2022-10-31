package org.gefsu.http.response;

import org.gefsu.http.request.HttpRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class GetResponder implements Responder {

    Properties mimeTypes = new Properties();

    @Override
    public void respond(HttpRequest request, OutputStream socketOut)
        throws IOException {

        // FIXME If there is no properties file this will produce a NullPointerException
        // FIXME You might want to be able to close this stream
        mimeTypes.load(new FileInputStream(
            getClass()
                .getResource("/mimetypes.properties")
                .getPath()));

        // FIXME Not implemented
        var fileName = request.getResource();
        var extension = determineFileExtension(fileName);
        var fileUrl = getClass().getResource(fileName);
        var mimeType = mimeTypes.getProperty(extension);
        System.out.println(mimeType);
    }

    private String determineFileExtension(String fileName) {
        var dot = fileName.lastIndexOf('.');
        try {
            return fileName.substring(dot+1);
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

}
