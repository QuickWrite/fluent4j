package net.quickwrite.fluent4j.impl.parser.base.entry;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.ast.entry.FluentTerm;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;

import java.util.List;
import java.util.Optional;

public class FluentTermParser extends FluentEntryParser<FluentTerm> {
    public FluentTermParser(FluentContentParser patternParser) {
        super(patternParser);
    }

    @Override
    protected FluentTerm getInstance(final String identifier, List<FluentPattern> patterns) {
        return new FluentTerm(identifier, patterns);
    }

    @Override
    protected Optional<String> getIdentifier(final ContentIterator content) {
        if (content.character() != '-') {
            return Optional.empty();
        }

        if (!isFluentIdentifierStart(content.nextChar())) {
            return Optional.empty();
        }

        final int position = content.position()[1];

        while (isFluentIdentifierPart(content.nextChar()));

        return Optional.of(content.line().substring(position, content.position()[1]));
    }
}
