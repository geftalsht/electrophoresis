package org.gefsu.server;

import org.gefsu.configuration.ServerConfiguration;
import org.jetbrains.annotations.NotNull;

public interface IServer {
    void start(@NotNull ServerConfiguration configuration);

    void stop();
}
