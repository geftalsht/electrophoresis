package org.gefsu;

import java.util.Arrays;
import java.util.Optional;

public class App {
    public static void main(String[] args) {

        Arrays.stream(args)
            .findFirst()
            .flatMap(App::safeParseInt)
            .filter(App::portIsValid)
            .ifPresentOrElse(
                Server::start,
                () -> System.out.println("Pass a port number as an argument!"));
    }

    private static Optional<Integer> safeParseInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static boolean portIsValid(int port) {
        return port >= 0 && port <= 65535;
    }

}
