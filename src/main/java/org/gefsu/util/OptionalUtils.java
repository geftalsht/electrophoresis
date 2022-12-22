package org.gefsu.util;

import java.util.Optional;

public class OptionalUtils {
    @FunctionalInterface
    public interface Producer<T> {
        T get() throws Exception;
    }

    public static <T> Optional<T> lift(Producer<T> f) {
        try {
            return Optional.ofNullable(f.get());
        } catch (Throwable e) {
            return Optional.empty();
        }
    }
}
