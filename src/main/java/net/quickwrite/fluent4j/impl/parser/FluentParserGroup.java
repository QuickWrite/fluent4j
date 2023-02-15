package net.quickwrite.fluent4j.impl.parser;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.container.exception.FluentBuilderException;
import net.quickwrite.fluent4j.impl.container.FluentEntryResource;
import net.quickwrite.fluent4j.impl.parser.base.CommentSkipper;
import net.quickwrite.fluent4j.impl.parser.base.WhitespaceSkipper;
import net.quickwrite.fluent4j.impl.parser.base.entry.FluentMessageParser;
import net.quickwrite.fluent4j.impl.parser.base.entry.FluentTermParser;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.FluentParser;
import net.quickwrite.fluent4j.parser.base.FluentElementParser;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

import java.util.ArrayList;
import java.util.List;

public final class FluentParserGroup implements FluentParser<FluentResource> {
    private final List<FluentElementParser<? extends FluentEntry>> baseParserList = new ArrayList<>();

    public static FluentParserGroup getBasicParser() {
        final FluentParserGroup group = new FluentParserGroup();

        group.addParser(new WhitespaceSkipper());
        group.addParser(new CommentSkipper());

        group.addParser(new FluentTermParser(FluentContentParser.DEFAULT_PARSER));
        group.addParser(new FluentMessageParser(FluentContentParser.DEFAULT_PARSER));

        return group;
    }

    public void addParser(final FluentElementParser<? extends FluentEntry> parser) {
        this.baseParserList.add(parser);
    }

    @Override
    public FluentResource parse(final ContentIterator iterator) {
        final List<FluentEntry> elements = new ArrayList<>();

        outer:
        while (iterator.line() != null) {
            final int[] position = iterator.position();

            for (final FluentElementParser<? extends FluentEntry> parser : baseParserList) {
                final ParseResult<? extends FluentEntry> result = parser.parse(iterator);

                switch (result.getType()) {
                    case SUCCESS:
                        elements.add(result.getValue());
                    case SKIP:
                        continue outer;
                }

                iterator.setPosition(position);
            }

            // TODO: Something went wrong and it must be handled
            throw new FluentBuilderException("Every parser returned FAILURE.", iterator);
        }

        return new FluentEntryResource(elements);
    }
}
