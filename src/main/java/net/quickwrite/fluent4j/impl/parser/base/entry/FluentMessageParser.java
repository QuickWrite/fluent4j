package net.quickwrite.fluent4j.impl.parser.base.entry;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.ast.entry.FluentMessage;
import net.quickwrite.fluent4j.impl.util.ParserUtil;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;

import java.util.List;
import java.util.Optional;

public final class FluentMessageParser extends FluentEntryParser<FluentMessage> {
    public FluentMessageParser(final FluentContentParser patternParser) {
        super(patternParser);
    }

    @Override
    protected FluentMessage getInstance(final String identifier, final List<FluentPattern> patterns) {
        return new FluentMessage(identifier, patterns);
    }

    @Override
    protected Optional<String> getIdentifier(final ContentIterator content) {
        return ParserUtil.getIdentifier(content);
    }
}
