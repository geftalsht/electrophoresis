package org.gefsu;

import org.gefsu.http.HttpRequest;
import org.gefsu.http.HttpResponseMetaBuilder;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import static org.gefsu.ServerSettings.SETTINGS;

public class GetHandler extends HttpHandler {

    @Override
    public void handle(OutputStream outputStream, Optional<HttpRequest> request) {
        request.map(this::requestedFileExists)
            .ifPresentOrElse(
                file -> {
                    if (!file)
                        ErrorHandler.notFound().handle(outputStream, request);
                    else
                        //noinspection OptionalGetWithoutIsPresent
                        sendFile(outputStream, request.get().getResource());},
                () -> ErrorHandler.badRequest().handle(outputStream, request));
    }

    private void sendFile(OutputStream outputStream, String fileName) {

       var response = new HttpResponseMetaBuilder()
           .setStatusCode(200)
           .setMimeType(SETTINGS.CONFIG.getProperty(determineFileExtension(fileName)))
           .build();

       try (var fis = getClass()
           .getResourceAsStream(SETTINGS.CONFIG.getProperty("rootPath") + fileName)) {

           outputStream.write(response.metaToBytes());
           writeIt(fis, outputStream);
       } catch (Exception e) {
           System.out.println("Error writing file to the OutputStream");
       }

    }

    private String determineFileExtension(String fileName) {
        var dot = fileName.lastIndexOf('.');
        if (dot != -1)
            return fileName.substring(dot+1);
        return "binary";
    }

    private boolean requestedFileExists(HttpRequest request) {
        try {
            return new File(getClass()
                .getResource(SETTINGS.CONFIG.getProperty("rootPath") + request.getResource())
                .toURI())
                .isFile();
        } catch (Exception e) {
            return false;
        }

    }

    private void writeIt(InputStream in, OutputStream out)
        throws IOException {

        byte[] buf = new byte[1024];
        int len;

        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
    }

}
