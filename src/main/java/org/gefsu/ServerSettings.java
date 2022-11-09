package org.gefsu;

import java.io.IOException;
import java.util.Properties;

public class ServerSettings {

    public static final ServerSettings SETTINGS = new ServerSettings();
    public final Properties CONFIG;

   private ServerSettings() {
       CONFIG = new Properties();
       try (final var fis =
                getClass().getResourceAsStream("/config.properties")) {
           CONFIG.load(fis);
       } catch (IOException e) {
           System.out.println("Failed to load the server configuration");
       }
   }

}
