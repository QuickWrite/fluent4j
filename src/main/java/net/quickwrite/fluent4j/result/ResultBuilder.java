package net.quickwrite.fluent4j.result;

public interface ResultBuilder {
    ResultBuilder append(final CharSequence sequence);

    ResultBuilder append(final char character);

    <T extends ResultBuilder> T getSimpleBuilder();
}
