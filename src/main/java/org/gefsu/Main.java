package org.gefsu;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Arrays.stream(args)
            .findFirst()
            .flatMap(x -> OptionalUtils.lift(() -> Integer.parseInt(x)))
            .filter(Main::portIsValid)
            .flatMap(Server::makeServer)
            .ifPresentOrElse(
                Server::listen,
                () -> System.out.println("Server failed to start."));
    }

    private static boolean portIsValid(int port) {
        return port >= 0 && port <= 65535;
    }

}
