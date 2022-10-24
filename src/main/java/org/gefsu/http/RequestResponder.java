package org.gefsu.http;

import java.io.OutputStream;

public interface RequestResponder {
    void respond(String clientRequest, OutputStream socketOut);

}
