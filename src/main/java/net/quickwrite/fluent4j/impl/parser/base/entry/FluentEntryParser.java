package net.quickwrite.fluent4j.impl.parser.base.entry;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.ast.entry.FluentEntryBase;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.base.FluentElementParser;
import net.quickwrite.fluent4j.parser.pattern.FluentPatternParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

import java.util.List;
import java.util.Optional;

public abstract class FluentEntryParser<T extends FluentEntryBase> implements FluentElementParser<T> {
    private final FluentPatternParser patternParser;

    public FluentEntryParser(final FluentPatternParser patternParser) {
        this.patternParser = patternParser;
    }

    @Override
    public ParseResult<T> parse(final ContentIterator content) {
        final Optional<String> identifier = getIdentifier(content);

        if (identifier.isEmpty()) {
            return ParseResult.failure();
        }

        skipWhitespace(content);

        if (content.character() != '=') {
            // TODO: Better exception
            throw new RuntimeException("Expected '=', but got '" + Character.toString(content.character()) +  "'");
        }

        final List<FluentPattern> patterns = patternParser.parse(content);

        return ParseResult.success(getInstance(identifier.get(), patterns));
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

    protected abstract T getInstance(final String identifier, List<FluentPattern> patterns);

    protected abstract Optional<String> getIdentifier(final ContentIterator content);
}
