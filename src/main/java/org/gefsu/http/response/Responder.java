package org.gefsu.http.response;

import org.gefsu.http.request.HttpRequest;
import java.io.IOException;
import java.io.OutputStream;

// FIXME DETAILS BELOW
// It's not actually a good idea to create a billion classes
// implementing this interface for the sake of smallest of changes.
// Imagine we have a separate class for responding with every single
// status code in existence. Not exactly smart, innit?
// I will think of a better solution later.
// Maybe.
public interface Responder {
    void respond(HttpRequest request, OutputStream socketOut)
        throws IOException;

}
