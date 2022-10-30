package org.gefsu.http.response;

import org.gefsu.http.request.HttpRequest;
import java.io.IOException;
import java.io.OutputStream;

public class NotAllowedResponder implements Responder {

    @Override
    public void respond(HttpRequest request, OutputStream socketOut)
        throws IOException {
        var builder = new HttpResponseBuilderImpl();
        var director = new HttpResponseDirector();

        director.buildMethodNotAllowedResponse(builder);
        var response = builder.build();

        socketOut.write(response.toBytes());
    }
}
