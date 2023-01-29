package net.quickwrite.fluent4j.impl.parser.base.entry;

import net.quickwrite.fluent4j.impl.ast.entry.FluentTerm;
import net.quickwrite.fluent4j.iterator.ContentIterator;

import java.util.Optional;

public class FluentTermParser extends FluentEntryParser<FluentTerm> {
    @Override
    protected FluentTerm getInstance(final String identifier) {
        return new FluentTerm(identifier);
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
