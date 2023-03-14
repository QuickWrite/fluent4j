package net.quickwrite.fluent4j.impl.result;

import net.quickwrite.fluent4j.result.ResultBuilder;

public class StringResultBuilder implements ResultBuilder {
    private final StringBuilder builder;

    public StringResultBuilder() {
        this.builder = new StringBuilder();
    }

    @Override
    public ResultBuilder append(final CharSequence sequence) {
        this.builder.append(sequence);

        return this;
    }

    @Override
    public ResultBuilder append(final char character) {
        this.builder.append(character);

        return this;
    }

    @Override
    public ResultBuilder append(final int character) {
        this.builder.appendCodePoint(character);

        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ResultBuilder> T getSimpleBuilder() {
        return (T) new StringResultBuilder();
    }

    @Override
    public String toString() {
        return this.builder.toString();
    }
}
