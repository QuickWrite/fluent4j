package net.quickwrite.fluent4j.impl.parser.base.entry;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.ast.entry.FluentMessageElement;
import net.quickwrite.fluent4j.impl.util.ParserUtil;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.List;
import java.util.Optional;

public final class FluentMessageParser<B extends ResultBuilder> extends FluentEntryParser<FluentMessageElement<B>, B> {
    public FluentMessageParser(final FluentContentParser<B> patternParser) {
        super(patternParser);
    }

    @Override
    protected FluentMessageElement<B> getInstance(final String identifier,
                                                  final List<FluentPattern<B>> patterns,
                                                  final List<FluentEntry.Attribute<B>> attributes
    ) {
        return new FluentMessageElement<>(identifier, patterns, attributes);
    }

    @Override
    protected Optional<String> getIdentifier(final ContentIterator content) {
        return ParserUtil.getIdentifier(content);
    }
}
