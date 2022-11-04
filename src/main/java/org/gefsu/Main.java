package org.gefsu;

import java.util.Arrays;
import java.util.Properties;

public class Main {

    public static final Properties CONFIG = new Properties();
    public static final String CONTENTROOT = "/html";

    public static void main(String[] args) {

        try (final var fin =
                 Main.class.getResourceAsStream("/mimetypes.properties")) {
            CONFIG.load(fin);
        } catch (Exception e) {
            System.out.println("Failed to read server configuration. Terminating.");
            return;
        }

        Arrays.stream(args)
            .findFirst()
            .flatMap(x -> OptionalUtils.lift(() -> Integer.parseInt(x)))
            .filter(Main::portIsValid)
            .flatMap(Server::makeServer)
            .ifPresentOrElse(
                Server::listen,
                () -> System.out.println("Server failed to start. " +
                    "Make sure you provided a valid port as an argument."));
    }

    private static boolean portIsValid(int port) {
        return port >= 0 && port <= 65535;
    }

}
