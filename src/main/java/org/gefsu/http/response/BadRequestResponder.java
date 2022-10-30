package org.gefsu.http.response;

import java.io.IOException;
import java.io.OutputStream;

public class BadRequestResponder {

    public static void respond(OutputStream socketOut)
        throws IOException {

        HttpResponse response = new HttpResponseBuilderImpl()
            .statusCode(400)
            .build();

        socketOut.write(response.toBytes());
    }

}
