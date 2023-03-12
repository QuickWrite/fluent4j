package net.quickwrite.fluent4j.result;

import net.quickwrite.fluent4j.impl.result.StringResultBuilder;

public interface ResultBuilder {
    ResultBuilder append(final CharSequence sequence);

    ResultBuilder append(final char character);

    ResultBuilder append(final int character);

    <T extends ResultBuilder> T getSimpleBuilder();

    static ResultBuilder get() {
        return new StringResultBuilder();
    }
}
