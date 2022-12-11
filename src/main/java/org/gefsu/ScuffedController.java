package org.gefsu;

import org.gefsu.http.HttpMethod;

import java.io.File;
import java.net.URI;
import java.util.Optional;
import static org.gefsu.OptionalUtils.lift;

public class ScuffedController {
    private final ServerSettings settings;

    public ScuffedController(ServerSettings settings) {
        this.settings = settings;
    }

    @SuppressWarnings({"Convert2MethodRef", "DataFlowIssue"})
    @HttpRequestMapping(method = HttpMethod.GET, url = "/*")
    public Optional<URI> getStaticResource(String resource) {
        return lift(() -> new File(getClass()
            .getResource(settings.config.getProperty("rootPath") + resource)
            .toURI()))
            .filter(file -> file.isFile())
            .map(file -> file.toURI());
    }
}
