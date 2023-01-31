package net.quickwrite.fluent4j.impl.util;

import net.quickwrite.fluent4j.iterator.ContentIterator;

import java.util.Optional;

public final class ParserUtil {
    private ParserUtil() {}

    private static boolean isFluentIdentifierStart(final int character) {
        return character >= 'a' && character <= 'z'
                || character >= 'A' && character <= 'Z';
    }

    private static boolean isFluentIdentifierPart(final int character) {
        return isFluentIdentifierStart(character)
                || character >= '0' && character <= '9'
                || character == '-'
                || character == '_';
    }

    public static Optional<String> getIdentifier(final ContentIterator content) {
        if (!isFluentIdentifierStart(content.character())) {
            return Optional.empty();
        }

        final int position = content.position()[1];

        while (isFluentIdentifierPart(content.nextChar()));

        return Optional.of(content.line().substring(position, content.position()[1]));
    }

    public static void skipWhitespace(final ContentIterator content) {
        while (Character.isWhitespace(content.character())) {
            if (content.character() == '\n') {
                // TODO: Better exception
                throw new RuntimeException("Expected token but got '\\n'");
            }

            content.nextChar();
        }
    }

    public static void skipWhitespaceAndNL(final ContentIterator iterator) {
        while (Character.isWhitespace(iterator.character())) {
            iterator.nextChar();
        }
    }
}
