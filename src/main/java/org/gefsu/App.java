package org.gefsu;

import org.gefsu.configuration.ConfigurationManager;
import org.gefsu.configuration.IConfigurationManager;
import org.gefsu.server.IServer;
import org.gefsu.server.Server;

public class App
{
    public static void main(String[] args) {

        IConfigurationManager configurationManager = new ConfigurationManager();
        IServer server = new Server();

        server.start(configurationManager.loadServerConfiguration());
    }
}
