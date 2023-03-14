package net.quickwrite.fluent4j.util;

import java.util.function.Consumer;

public interface Builder<R> {
    /**
     * Configures {@code builder} using {@code consumer} and then builds.
     *
     * @param builder the builder
     * @param consumer the builder consume
     * @param <R> the type to be built
     * @param <B> the builder type
     * @return the built thing
     */
    static <R, B extends Builder<R>> R configureAndBuild(final B builder, final Consumer<? super B> consumer) {
        if (consumer != null) {
            consumer.accept(builder);
        }
        return builder.build();
    }

    R build();
}
