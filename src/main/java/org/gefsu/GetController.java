package org.gefsu;

import org.gefsu.annotation.Controller;
import org.gefsu.annotation.HttpRequestMapping;
import org.gefsu.http.HttpMethod;
import org.gefsu.http.HttpResponse;
import org.gefsu.http.HttpResponseBuilder;

import java.io.File;

import static org.gefsu.util.OptionalUtils.lift;

@Controller
public class GetController {
    private final ServerSettings settings;

    public GetController(ServerSettings settings) {
        this.settings = settings;
    }

    @SuppressWarnings({"Convert2MethodRef", "DataFlowIssue"})
    @HttpRequestMapping(method = HttpMethod.GET, url = "/(.*)")
    public HttpResponse getStaticResource(String resource) {
        return lift(() -> new File(getClass()
            .getResource(settings.config.getProperty("rootPath") + resource)
            .toURI()))
            .filter(file -> file.isFile())
            .map(file -> file.toURI())
            .map(uri -> new HttpResponseBuilder()
                .buildGetOKResponse(uri, settings))
            .orElseGet(() -> new HttpResponseBuilder()
                .buildSimpleErrorResponse(404));
    }
}
