package org.gefsu;

import org.gefsu.http.HttpMethod;

import java.io.File;

public class ScuffedController {
    private final ServerSettings settings;

    public ScuffedController(ServerSettings settings) {
        this.settings = settings;
    }

    @HttpRequestMapping(method = HttpMethod.GET, url = "/*")
    public String getStaticResource(String resource) {
        // TODO Reimplement logic
        return null;
    }

    private boolean requestedFileExists(String resource) {
        try {
            return new File(getClass()
                .getResource(settings.config.getProperty("rootPath") + resource)
                .toURI())
                .isFile();
        } catch (Exception e) {
            return false;
        }
    }
}
