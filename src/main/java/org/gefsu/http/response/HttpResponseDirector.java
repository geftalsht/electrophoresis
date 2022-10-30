package org.gefsu.http.response;

// Write here once, don't write anywhere else.
public class HttpResponseDirector {

    public void buildBadResponse(HttpResponse.Builder builder) {
        builder.setStatusCode(400);
    }

    public void buildForbiddenResponse(HttpResponse.Builder builder) {
        builder.setStatusCode(403);
    }

    public void buildNotFoundResponse(HttpResponse.Builder builder) {
        builder.setStatusCode(404);
    }

    public void buildMethodNotAllowedResponse(HttpResponse.Builder builder) {
        builder.setStatusCode(405);
    }

    public void buildOKResponse(HttpResponse.Builder builder) {
        builder.setStatusCode(200);
    }

}
