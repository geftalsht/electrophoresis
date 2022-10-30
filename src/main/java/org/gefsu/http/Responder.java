package org.gefsu.http;

import java.io.OutputStream;

public interface Responder {
    void respond(HttpRequest request, OutputStream socketOut);

}
