package net.quickwrite.fluent4j.exception;

import net.quickwrite.fluent4j.result.ResultBuilder;

public class FluentPatternException extends FluentException {
    private final DataWriter dataWriter;

    protected FluentPatternException(final DataWriter dataWriter) {
        this.dataWriter = dataWriter;
    }

    public static FluentPatternException getDefault(final DataWriter dataWriter) {
        return new FluentPatternException(dataWriter);
    }

    public DataWriter getDataWriter() {
        return dataWriter;
    }

    public interface DataWriter {
        void write(final ResultBuilder builder);
    }
}
