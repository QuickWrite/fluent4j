package net.quickwrite.fluent4j.exception;

import net.quickwrite.fluent4j.iterator.ContentIterator;

import java.util.Arrays;

public class FluentBuilderException extends RuntimeException {
    private final int[] position;
    private final String line;
    private final String inputName;

    public FluentBuilderException(final String message, final ContentIterator iterator) {
        super(message);

        this.position = iterator.position();
        this.line = iterator.line();
        this.inputName = iterator.inputName();
    }

    public FluentBuilderException(final String message, final ContentIterator iterator, final Throwable cause) {
        super(message, cause);

        this.position = iterator.position();
        this.line = iterator.line();
        this.inputName = iterator.inputName();
    }

    @Override
    public String getLocalizedMessage() {
        final String lineNumber = Integer.toString(position[0] + 1);
        final StringBuilder builder = new StringBuilder();

        final char[] whitespace = getWhitespace(lineNumber.length());
        builder.append("An error while parsing a fluent resource occurred:\n");

        appendWithWhitespace(
                whitespace,
                builder,
                "--> " + inputName + ":" + lineNumber + ":" + (position[1] + 1),
                " |"
                );
        builder.append(lineNumber)
                .append(" |   ")
                .append(line)
                .append('\n');

        appendWithWhitespace(
                whitespace,
                builder,
                " |   " + new String(getWhitespace(position[1])) + "^ " + getMessage(),
                " |"
                );

        return builder.toString();
    }

    private void appendWithWhitespace(final char[] whitespace, final StringBuilder builder, final String... elements) {
        for (final String element : elements) {
            builder.append(whitespace)
                    .append(element)
                    .append('\n');
        }
    }

    private char[] getWhitespace(final int length) {
        final char[] whitespace = new char[length];
        Arrays.fill(whitespace, ' ');
        return whitespace;
    }
}
