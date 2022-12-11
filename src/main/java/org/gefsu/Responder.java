package org.gefsu;

import java.io.OutputStream;
import java.net.URI;
import java.util.Optional;

public class Responder {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static void writeResponse(
        OutputStream outputStream, Optional<URI> uri)
    {
        uri.ifPresentOrElse(
            uri1 -> {

        },
            () -> {

        });
    }
}
