package org.gefsu;

import java.io.IOException;
import java.util.Properties;

public class ServerSettings {
   public final Properties config;

   public ServerSettings() throws IOException {
       config = new Properties();
       try (final var fis = getClass()
               .getResourceAsStream("/config.properties"))
       {
           config.load(fis);
       }
   }
}
