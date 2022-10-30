package org.gefsu.http.response;

import java.io.IOException;
import java.io.OutputStream;

public class BadRequestResponder {

    public static void respond(OutputStream socketOut)
        throws IOException {
        var builder = new HttpResponseBuilderImpl();
        var director = new HttpResponseDirector();

        director.buildBadResponse(builder);
        var response = builder.build();

        socketOut.write(response.toBytes());
    }

}
