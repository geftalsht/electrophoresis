package org.gefsu;

import org.gefsu.http.HttpMethod;

public class ScuffedController {
    @HttpRequestMapping(method = HttpMethod.GET, url = "/*")
    public String getStaticResource(String resource) {
        // TODO Reimplement logic
        return null;
    }
}
