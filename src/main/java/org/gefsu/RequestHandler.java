package org.gefsu;

import org.gefsu.http.HttpRequest;
import org.gefsu.http.HttpResponse;

public interface RequestHandler {
    HttpResponse handleRequest(HttpRequest request);
}
