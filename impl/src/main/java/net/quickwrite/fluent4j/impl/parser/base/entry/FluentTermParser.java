package net.quickwrite.fluent4j.impl.parser.base.entry;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.entry.FluentAttributeEntry;
import net.quickwrite.fluent4j.impl.ast.entry.FluentTermElement;
import net.quickwrite.fluent4j.impl.util.ParserUtil;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;

import java.util.Optional;

public class FluentTermParser extends FluentEntryParser<FluentTermElement> {
    public FluentTermParser(final FluentContentParser patternParser) {
        super(patternParser);
    }

    @Override
    protected FluentTermElement getInstance(final String identifier,
                                            final FluentPattern[] patterns,
                                            final FluentAttributeEntry.Attribute[] attributes
    ) {
        return new FluentTermElement(identifier, patterns, attributes);
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
