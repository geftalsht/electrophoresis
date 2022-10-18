package org.gefsu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

class ConfigurationManagerTest {

    // Configuration can be loaded from the YAML file
    @Test
    void loadServerConfiguration() {
        Yaml yaml = new Yaml();

        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("serverconfigPort8080.yml");

        ServerConfiguration serverConfiguration = yaml.load(inputStream);

        Assertions.assertEquals(8080, serverConfiguration.getPortNumber());
    }
}
