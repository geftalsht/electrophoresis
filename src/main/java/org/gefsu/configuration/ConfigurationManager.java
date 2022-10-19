package org.gefsu.configuration;

import org.yaml.snakeyaml.Yaml;
import java.io.IOException;
import java.io.InputStream;

public class ConfigurationManager implements IConfigurationManager {

    // Loads server configuration from a YAML file
    public ServerConfiguration loadServerConfiguration() {

        ServerConfiguration serverConfiguration = null;

        Yaml yaml = new Yaml();

        try (InputStream inputStream = getClass()
                             .getClassLoader()
                             .getResourceAsStream("config/serverconfig.yml"))
        {
            serverConfiguration = yaml.load(inputStream);
        }
        catch (IOException e) {
            System.out.println("Error reading configuration file");
        }

        if (serverConfiguration == null) {
            serverConfiguration = new ServerConfiguration();
        }

        return serverConfiguration;
    }
}
