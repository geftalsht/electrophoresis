package org.gefsu;

public class App
{
    public static void main(String[] args) {

        IConfigurationManager configurationManager = new ConfigurationManager();
        ServerConfiguration serverConfiguration = configurationManager.loadServerConfiguration();
        IElectrophoresisServer server = new ElectrophoresisServer();

        server.start(serverConfiguration);
    }
}
