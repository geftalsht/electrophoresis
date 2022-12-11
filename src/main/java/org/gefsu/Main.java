package org.gefsu;

import java.io.IOException;
import java.util.Arrays;

import static org.gefsu.OptionalUtils.lift;

public class Main {
    public static void main(String[] args) {
        ServerSettings settings;
        try {
            settings = new ServerSettings();
        } catch (IOException e) {
            System.out.println("Failed to load server settings.");
            return;
        }

        //noinspection Convert2MethodRef
        Arrays.stream(args)
            .findFirst()
            .flatMap(x -> lift(() -> Integer.parseInt(x)))
            .filter(port -> portIsValid(port))
            .flatMap(port -> Server.makeServer(port, settings))
            .ifPresentOrElse(
                server -> server.listen(),
                () -> System.out.println("Server failed to start. " +
                    "Make sure you provided a valid port as an argument."));
    }

    private static boolean portIsValid(int port) {
        return port >= 0 && port <= 65535;
    }
}
