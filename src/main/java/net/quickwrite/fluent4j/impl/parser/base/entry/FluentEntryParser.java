package net.quickwrite.fluent4j.impl.parser.base.entry;

import net.quickwrite.fluent4j.impl.ast.entry.FluentEntryBase;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.base.FluentParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

import java.util.Optional;

public abstract class FluentEntryParser<T extends FluentEntryBase> implements FluentParser<T> {
    @Override
    public ParseResult<T> tryParse(final ContentIterator content) {
        final Optional<String> identifier = getIdentifier(content);

        if (identifier.isEmpty()) {
            return ParseResult.failure();
        }

        skipWhitespace(content);

        if (content.character() != '=') {
            // TODO: Better exception
            throw new RuntimeException("Expected '=', but got '" + Character.toString(content.character()) +  "'");
        }

        // TODO: Generate the tree
        content.nextLine();

        return ParseResult.success(getInstance(identifier.get()));
    }

    private void skipWhitespace(final ContentIterator content) {
        while (Character.isWhitespace(content.character())) {
            if (content.character() == '\n') {
                // TODO: Better exception
                throw new RuntimeException("Expected token but got '\\n'");
            }

            content.nextChar();
        }
    }

    protected boolean isFluentIdentifierStart(final int character) {
        return character >= 'a' && character <= 'z'
                || character >= 'A' && character <= 'Z';
    }

    protected boolean isFluentIdentifierPart(final int character) {
        return isFluentIdentifierStart(character)
                || character >= '0' && character <= '9'
                || character == '-'
                || character == '_';
    }

    protected abstract T getInstance(final String identifier);

    protected abstract Optional<String> getIdentifier(final ContentIterator content);
}
