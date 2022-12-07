package org.gefsu;

import java.util.Arrays;

import static org.gefsu.OptionalUtils.lift;

public class Main {
    public static void main(String[] args) {
        Arrays.stream(args)
            .findFirst()
            .flatMap(x -> lift(() -> Integer.parseInt(x)))
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
