package net.quickwrite.fluent4j.impl.parser.pattern.placeable;

import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.exception.FluentBuilderException;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentTextElement;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableParser;

import java.util.Optional;

public class FluentStringLiteralParser implements PlaceableExpressionParser {
    @Override
    public Optional<FluentPlaceable> parse(final ContentIterator iterator, final PlaceableParser placeableParser) {
        if (iterator.character() != '"') {
            return Optional.empty();
        }

        iterator.nextChar();

        final StringBuilder builder = new StringBuilder();

        while (iterator.character() != '"') {
            if (iterator.character() == '\n') {
                throw new FluentBuilderException("Unterminated string expression", iterator);
            }

            if (iterator.character() == '\\') {
                final int character = iterator.nextChar();

                switch (character) {
                    case '"' -> builder.append('"');
                    case '\\' -> builder.append('\\');
                    case 'u' -> builder.appendCodePoint(parseCharacters(iterator, 4));
                    case 'U' -> builder.appendCodePoint(parseCharacters(iterator, 6));

                    default -> throw new FluentBuilderException(
                            "Unknown escape character: '" + Character.toString(character) + "'",
                            iterator
                    );
                }

                iterator.nextChar();

                continue;
            }
            builder.appendCodePoint(iterator.character());

            iterator.nextChar();
        }

        iterator.nextChar();

        return Optional.of(new FluentTextElement(builder.toString()));
    }

    private int parseCharacters(final ContentIterator iterator, final int amount) {
        final char[] chars = new char[amount];

        for (int i = 0; i < amount; i++) {
            final int character = iterator.nextChar();

            if (!isHexChar(character)) {
                throw new FluentBuilderException(
                        "Invalid unicode escape character '" + Character.toString(character) + "'",
                        iterator
                );
            }

            chars[i] = (char) character;
        }

        return Integer.parseInt(String.valueOf(chars), 16);
    }

    private boolean isHexChar(final int character) {
        return character >= '0' && character <= '9'
                || character >= 'a' && character <= 'f'
                || character >= 'A' && character <= 'F';
    }
}
