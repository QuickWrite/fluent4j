package net.quickwrite.fluent4j.util;

import java.util.function.Consumer;

/**
 * The Builder interface represents a generic builder.
 *
 * @param <R> the type to be built
 */
public interface Builder<R> {

    /**
     * Configures the builder using the provided consumer and then builds the object.
     *
     * @param builder  the builder instance
     * @param consumer the consumer to configure the builder
     * @param <R>      the type to be built
     * @param <B>      the builder type
     * @return the built object
     */
    static <R, B extends Builder<R>> R configureAndBuild(final B builder, final Consumer<? super B> consumer) {
        if (consumer != null) {
            consumer.accept(builder);
        }
        return builder.build();
    }

    /**
     * Builds the object.
     *
     * @return the built object
     */
    R build();
}
