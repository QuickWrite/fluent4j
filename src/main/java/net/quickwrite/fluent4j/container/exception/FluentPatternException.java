package net.quickwrite.fluent4j.container.exception;

import net.quickwrite.fluent4j.result.ResultBuilder;

public class FluentPatternException extends RuntimeException {
    private final DefaultDataWriter defaultDataWriter;

    protected FluentPatternException(final DefaultDataWriter defaultDataWriter) {
        this.defaultDataWriter = defaultDataWriter;
    }

    public static FluentPatternException getDefault(final DefaultDataWriter defaultDataWriter) {
        return new FluentPatternException(defaultDataWriter);
    }

    public static FluentPatternException getPlaceable(final PlaceableDataWriter placeableDataWriter) {
        return getDefault(placeableDataWriter);
    }

    public DefaultDataWriter getDefaultDataWriter() {
        return defaultDataWriter;
    }

    public interface DefaultDataWriter {
        void write(final ResultBuilder builder);
    }

    public interface PlaceableDataWriter extends DefaultDataWriter{
        default void write(final ResultBuilder builder) {
            builder.append('{');
            writeDefault(builder);
            builder.append('}');
        }

        void writeDefault(final ResultBuilder appendable);
    }
}
