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

   public String determineMimeType(String fileName) {
       return config.getProperty(getFileExtension(fileName));
   }

   private String getFileExtension(String fileName) {
       var dot = fileName.lastIndexOf('.');
       if (dot != -1)
           return fileName.substring(dot+1);
       return "binary";
   }
}
