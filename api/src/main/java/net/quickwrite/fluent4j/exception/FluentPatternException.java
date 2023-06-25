package net.quickwrite.fluent4j.exception;

import net.quickwrite.fluent4j.result.ResultBuilder;

public class FluentPatternException extends Exception {
    private final DataWriter<? extends ResultBuilder> dataWriter;

    protected FluentPatternException(final DataWriter<? extends ResultBuilder> dataWriter) {
        this.dataWriter = dataWriter;
    }

    public static <B extends ResultBuilder> FluentPatternException getDefault(final DataWriter<B> dataWriter) {
        return new FluentPatternException(dataWriter);
    }

    @SuppressWarnings("unchecked")
    public <B extends ResultBuilder> DataWriter<B> getDataWriter() {
        return (DataWriter<B>) dataWriter;
    }

    public interface DataWriter<B extends ResultBuilder> {
        void write(final B builder);
    }
}
