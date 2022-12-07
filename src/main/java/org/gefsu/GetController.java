package org.gefsu;

import org.gefsu.http.HttpMethod;

@Controller
public class GetController {
    @HttpRequestMapping(method = HttpMethod.GET, url = "/{resource}")
    public String getStaticResource(String resource) {
        // TODO Reimplement logic
        return null;
    }
}
