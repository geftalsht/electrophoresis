package org.gefsu;

import org.gefsu.http.HttpResponseBuilderImpl;
import java.io.IOException;
import java.io.OutputStream;

public class RequestReceiver {

    public void getResource(String resourceName, OutputStream respondTo) {
        System.out.println("I've been asked to GET " + resourceName);
    }

    public void notAllowedRespond(OutputStream respondTo)
        throws IOException {

        var responseBuilder = new HttpResponseBuilderImpl<String>();
        responseBuilder.setStatusCode(405);

        var response = responseBuilder.build();
        respondTo.write(response.metaToBytes());

    }

    public void badRespond(OutputStream respondTo)
        throws IOException {

        var responseBuilder = new HttpResponseBuilderImpl<String>();
        responseBuilder.setStatusCode(400);

        var response = responseBuilder.build();
        respondTo.write(response.metaToBytes());
    }

}
