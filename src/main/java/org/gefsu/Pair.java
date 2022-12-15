package org.gefsu;

public class Pair<L,R> {
    private final L left;
    private final R right;

    public static <L,R> Pair<L,R> of(final L left, final R right) {
        return new Pair<>(left, right);
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    private Pair(final L left, final R right) {
        this.left = left;
        this.right = right;
    }
}
