package net.quickwrite.fluent4j.impl.parser.base.entry;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.ast.entry.FluentTermElement;
import net.quickwrite.fluent4j.impl.util.ParserUtil;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.List;
import java.util.Optional;

public class FluentTermParser<B extends ResultBuilder> extends FluentEntryParser<FluentTermElement<B>, B> {
    public FluentTermParser(final FluentContentParser<B> patternParser) {
        super(patternParser);
    }

    @Override
    protected FluentTermElement<B> getInstance(final String identifier,
                                               final List<FluentPattern<B>> patterns,
                                               final List<FluentEntry.Attribute<B>> attributes
    ) {
        return new FluentTermElement<B>(identifier, patterns, attributes);
    }

    @Override
    protected Optional<String> getIdentifier(final ContentIterator content) {
        if (content.character() != '-') {
            return Optional.empty();
        }

        content.nextChar();

        return ParserUtil.getIdentifier(content);
    }
}
