package net.quickwrite.fluent4j.util;

/**
 * The Builder interface represents a generic builder.
 *
 * @param <R> the type to be built
 */
public interface Builder<R> {
    /**
     * Builds the object.
     *
     * @return the built object
     */
    R build();
}
