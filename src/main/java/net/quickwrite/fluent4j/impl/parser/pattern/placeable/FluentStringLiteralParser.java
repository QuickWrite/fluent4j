package net.quickwrite.fluent4j.impl.parser.pattern.placeable;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.parser.pattern.IntermediateTextElement;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

import java.nio.CharBuffer;

public class FluentStringLiteralParser implements PlaceableExpressionParser<FluentPattern> {
    @Override
    public ParseResult<FluentPattern> parse(final ContentIterator iterator) {
        if(iterator.character() != '"') {
            return ParseResult.failure();
        }

        iterator.nextChar();

        final StringBuilder builder = new StringBuilder();

        while (iterator.character() != '"') {
            if (iterator.character() == '\n') {
                throw new RuntimeException("StringLiteral cannot have a \\n");
            }

            if (iterator.character() == '\\') {
                final int character = iterator.nextChar();

                switch (character) {
                    case '"' -> builder.append('"');
                    case '\\' -> builder.append('\\');
                    case 'u' -> builder.appendCodePoint(parseCharacters(iterator, 4));
                    case 'U' -> builder.appendCodePoint(parseCharacters(iterator, 6));

                    default -> throw new RuntimeException(
                            "Unknown escape character: '" +
                            Character.toString(character) +
                            "'"
                    );
                }

                iterator.nextChar();

                continue;
            }
            builder.appendCodePoint(iterator.character());

            iterator.nextChar();
        }

        iterator.nextChar();

        return ParseResult.success(
                new IntermediateTextElement(
                        CharBuffer.wrap(builder.toString()),
                        0,
                        false
                )
        );
    }

    private int parseCharacters(final ContentIterator iterator, final int amount) {
        final char[] chars = new char[amount];

        for (int i = 0; i < amount; i++) {
            final int character = iterator.nextChar();

            if (!isHexChar(character)) {
                throw new RuntimeException("Invalid unicode escape character '" + Character.toString(character) + "'");
            }

            chars[i] = (char)character;
        }

        return Integer.parseInt(String.valueOf(chars), 16);
    }

    private boolean isHexChar(final int character) {
        return character >= '0' && character <= '9'
                || character >= 'a' && character <= 'f'
                || character >= 'A' && character <= 'F';
    }

    @Override
    public boolean canSelectExpression() {
        return true;
    }
}
