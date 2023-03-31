package net.quickwrite.fluent4j.exception;

import net.quickwrite.fluent4j.iterator.ContentIterator;

public class FluentExpectedException extends FluentBuilderException {
    public FluentExpectedException(final int expected, final ContentIterator iterator) {
        super(generateMessage(expected, iterator.character()), iterator);
    }

    public FluentExpectedException(final String expected, final String got, final ContentIterator iterator) {
        super(generateMessage(expected, got), iterator);
    }

    public FluentExpectedException(final int expected, final ContentIterator iterator, final Throwable cause) {
        super(generateMessage(expected, iterator.character()), iterator, cause);
    }

    private static String generateMessage(final int expected, final int got) {
        return generateMessage(Character.toString(expected), Character.toString(got));
    }

    private static String generateMessage(final String expected, final String got) {
        return "Expected '" + expected +  "' but got '" + got + '\'';
    }
}
