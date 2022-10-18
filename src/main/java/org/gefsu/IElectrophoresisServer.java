package org.gefsu;

import org.jetbrains.annotations.NotNull;

public interface IElectrophoresisServer {
    void start(@NotNull ServerConfiguration configuration);

    void stop();
}
