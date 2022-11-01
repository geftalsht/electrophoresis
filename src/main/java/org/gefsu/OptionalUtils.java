package org.gefsu;

import java.util.Optional;

public class OptionalUtils {

    @FunctionalInterface
    public interface Producer<T> {
        T get() throws Exception;
    }

    @FunctionalInterface
    public interface F2<A,B> {
        void combine(A a, B b);
    }

    public static <T> Optional<T> lift(Producer<T> f) {
        try {
            return Optional.ofNullable(f.get());
        } catch (Throwable e) {
            return Optional.empty();
        }
    }

    public static <A,B,C> void ifPresent(Optional<A> oa, Optional<B> ob, F2<A,B> f) {
        if (oa.isPresent() && ob.isPresent())
            f.combine(oa.get(), ob.get());
    }

}
