package net.quickwrite.fluent4j.impl.parser.base.entry;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.ast.entry.FluentEntryBase;
import net.quickwrite.fluent4j.impl.util.ParserUtil;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.base.FluentElementParser;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

import java.util.List;
import java.util.Optional;

public abstract class FluentEntryParser<T extends FluentEntryBase> implements FluentElementParser<T> {
    private final FluentContentParser patternParser;

    public FluentEntryParser(final FluentContentParser patternParser) {
        this.patternParser = patternParser;
    }

    @Override
    public ParseResult<T> parse(final ContentIterator content) {
        final Optional<String> identifier = getIdentifier(content);

        if (identifier.isEmpty()) {
            return ParseResult.failure();
        }

        ParserUtil.skipWhitespace(content);

        if (content.character() != '=') {
            // TODO: Better exception
            throw new RuntimeException("Expected '=', but got '" + Character.toString(content.character()) + "'");
        }

        final List<FluentPattern> patterns = patternParser.parse(
                content,
                iterator -> !Character.isWhitespace(iterator.character())
        );

        return ParseResult.success(getInstance(identifier.get(), patterns));
    }

    protected abstract T getInstance(final String identifier, List<FluentPattern> patterns);

    protected abstract Optional<String> getIdentifier(final ContentIterator content);
}
