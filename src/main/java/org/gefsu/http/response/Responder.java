package org.gefsu.http.response;

import org.gefsu.http.request.HttpRequest;

import java.io.OutputStream;

public interface Responder {
    void respond(HttpRequest request, OutputStream socketOut);

}
