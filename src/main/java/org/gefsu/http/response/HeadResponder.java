package org.gefsu.http.response;

import org.gefsu.http.request.HttpRequest;
import java.io.IOException;
import java.io.OutputStream;

public class HeadResponder implements Responder {

    @Override
    public void respond(HttpRequest request, OutputStream socketOut)
        throws IOException {

    }
}
