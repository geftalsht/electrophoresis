package org.gefsu.http.response;

import org.gefsu.http.request.HttpRequest;
import java.io.OutputStream;

public class NotAllowedResponder implements Responder {

    @Override
    public void respond(HttpRequest request, OutputStream socketOut) {

    }
}
