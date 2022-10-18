package org.gefsu;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ServerConfiguration implements Serializable {
    private int portNumber;

    // Defaults
    public ServerConfiguration() {
        portNumber = 8080;
    }
}
