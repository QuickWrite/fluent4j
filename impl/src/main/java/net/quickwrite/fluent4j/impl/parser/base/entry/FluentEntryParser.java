package net.quickwrite.fluent4j.impl.parser.base.entry;

import net.quickwrite.fluent4j.ast.entry.FluentAttributeEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.exception.FluentBuilderException;
import net.quickwrite.fluent4j.exception.FluentExpectedException;
import net.quickwrite.fluent4j.impl.ast.entry.FluentAttribute;
import net.quickwrite.fluent4j.impl.ast.entry.FluentAttributeEntryBase;
import net.quickwrite.fluent4j.impl.util.ParserUtil;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.base.FluentElementParser;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class FluentEntryParser<T extends FluentAttributeEntryBase> implements FluentElementParser<T> {
    private final FluentContentParser patternParser;
    private static final FluentContentParser.EndChecker END_CHECKER = iterator -> {
        if (!Character.isWhitespace(iterator.character())) {
            return true;
        }

        ParserUtil.skipWhitespace(iterator);

        return iterator.character() == '.';
    };

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
            throw new FluentExpectedException('=', content);
        }

        content.nextChar();

        final List<FluentPattern> patterns = patternParser.parse(
                content,
                END_CHECKER
        );

        final List<FluentAttributeEntry.Attribute> attributes = getAttributes(content);

        return ParseResult.success(
                getInstance(
                        identifier.get(),
                        patterns.toArray(new FluentPattern[0]),
                        attributes.toArray(new FluentAttributeEntry.Attribute[0])
                )
        );
    }

    private List<FluentAttributeEntry.Attribute> getAttributes(final ContentIterator content) {
        if (content.character() != '.') {
            return List.of();
        }

        final List<FluentAttributeEntry.Attribute> attributes = new ArrayList<>();

        while (content.character() == '.') {
            content.nextChar();

            final String identifier = ParserUtil.getIdentifier(content)
                    .orElseThrow(() -> new FluentBuilderException("Expected identifier", content));

            ParserUtil.skipWhitespace(content);

            if (content.character() != '=') {
                throw new FluentExpectedException('=', content);
            }

            content.nextChar();

            final List<FluentPattern> patterns = patternParser.parse(
                    content,
                    END_CHECKER
            );

            attributes.add(new FluentAttribute(identifier, patterns.toArray(new FluentPattern[0])));
        }

        return attributes;
    }

    protected abstract T getInstance(
            final String identifier,
            final FluentPattern[] patterns,
            final FluentAttributeEntry.Attribute[] attributes
    );

    protected abstract Optional<String> getIdentifier(final ContentIterator content);
}
