package org.gefsu;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.gefsu.OptionalUtils.lift;

public class Main {
    @SuppressWarnings("Convert2MethodRef")
    public static void main(String[] args) {
        ServerSettings settings;

        // Load the settings
        try {
            settings = new ServerSettings();
        } catch (IOException e) {
            System.out.println("Failed to load server settings.");
            return;
        }

        // Create the component scanner and scan the classpath for controller classes
        final var componentScanner = new ComponentScanner();
        final Package pkg = componentScanner.getClass()
            .getPackage();
        final List<Object> controllerObjects = componentScanner
            .findClassesAnnotatedWith(pkg, Controller.class)
            .stream()
            .map(aClass ->
                lift(() -> aClass
                    .getConstructor(settings.getClass())
                    .newInstance(settings)))
            .filter(optionalObject -> optionalObject.isPresent())
            .map(object -> object.get())
            .toList();
        final HandlerMap requestHandlers = HandlerMap.create(controllerObjects);

        //noinspection Convert2MethodRef
        Arrays.stream(args)
            .findFirst()
            .flatMap(x -> lift(() -> Integer.parseInt(x)))
            .filter(port -> portIsValid(port))
            .flatMap(port -> Server.makeServer(port, requestHandlers))
            .ifPresentOrElse(
                server -> server.listen(),
                () -> System.out.println("Server failed to start. " +
                    "Make sure you provided a valid port as an argument."));
    }

    private static boolean portIsValid(int port) {
        return port >= 0 && port <= 65535;
    }
}
