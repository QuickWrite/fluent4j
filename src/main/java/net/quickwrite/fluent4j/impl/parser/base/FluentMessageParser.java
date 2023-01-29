package net.quickwrite.fluent4j.impl.parser.base;

import net.quickwrite.fluent4j.impl.ast.entry.FluentMessage;
import net.quickwrite.fluent4j.iterator.ContentIterator;

import java.util.Optional;

public final class FluentMessageParser extends FluentEntryParser<FluentMessage> {
    @Override
    protected FluentMessage getInstance(final String identifier) {
        return new FluentMessage(identifier);
    }

    @Override
    protected Optional<String> getIdentifier(final ContentIterator content) {
        if (!isFluentIdentifierStart(content.character())) {
            return Optional.empty();
        }

        final int position = content.position()[1];

        while (isFluentIdentifierPart(content.nextChar()));

        return Optional.of(content.line().substring(position, content.position()[1]));
    }
}
