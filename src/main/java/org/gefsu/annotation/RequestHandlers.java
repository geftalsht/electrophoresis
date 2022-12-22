package org.gefsu.annotation;

import org.gefsu.http.HttpMethod;
import org.gefsu.http.HttpResponse;

public interface RequestHandlers {
    HttpResponse invokeHandler(
        final HttpMethod requestMethod,
        final String requestResource
    );
}
