package net.quickwrite.fluent4j.impl.parser.base.entry;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.container.exception.FluentBuilderException;
import net.quickwrite.fluent4j.container.exception.FluentExpectedException;
import net.quickwrite.fluent4j.impl.ast.entry.FluentAttribute;
import net.quickwrite.fluent4j.impl.ast.entry.FluentEntryBase;
import net.quickwrite.fluent4j.impl.util.ParserUtil;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.base.FluentElementParser;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class FluentEntryParser<T extends FluentEntryBase, B extends ResultBuilder> implements FluentElementParser<T> {
    private final FluentContentParser<B> patternParser;
    private static final Function<ContentIterator, Boolean> END_CHECKER = iterator -> {
        if (!Character.isWhitespace(iterator.character())) {
            return true;
        }

        ParserUtil.skipWhitespace(iterator);

        return iterator.character() == '.';
    };

    public FluentEntryParser(final FluentContentParser<B> patternParser) {
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
            throw new FluentExpectedException('=', content);
        }

        content.nextChar();

        final List<FluentPattern<B>> patterns = patternParser.parse(
                content,
                END_CHECKER
        );

        final List<FluentEntry.Attribute<B>> attributes = getAttributes(content);

        return ParseResult.success(getInstance(identifier.get(), patterns, attributes));
    }

    private List<FluentEntry.Attribute<B>> getAttributes(final ContentIterator content) {
        if (content.character() != '.') {
            return List.of();
        }

        final List<FluentEntry.Attribute<B>> attributes = new ArrayList<>();

        while (content.character() == '.') {
            content.nextChar();

            final String identifier = ParserUtil.getIdentifier(content)
                    .orElseThrow(() -> new FluentBuilderException("Expected identifier", content));

            ParserUtil.skipWhitespace(content);

            if (content.character() != '=') {
                throw new FluentExpectedException('=', content);
            }

            content.nextChar();

            final List<FluentPattern<B>> patterns = patternParser.parse(
                    content,
                    END_CHECKER
            );

            attributes.add(new FluentAttribute<>(identifier, patterns));
        }

        return attributes;
    }

    protected abstract T getInstance(final String identifier, final List<FluentPattern<B>> patterns, final List<FluentEntry.Attribute<B>> attributes);

    protected abstract Optional<String> getIdentifier(final ContentIterator content);
}
