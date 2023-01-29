package net.quickwrite.fluent4j.impl.parser;

import net.quickwrite.fluent4j.ast.FluentEntry;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.impl.container.FluentEntryResource;
import net.quickwrite.fluent4j.impl.parser.base.CommentSkipper;
import net.quickwrite.fluent4j.impl.parser.base.entry.FluentMessageParser;
import net.quickwrite.fluent4j.impl.parser.base.WhitespaceSkipper;
import net.quickwrite.fluent4j.impl.parser.base.entry.FluentTermParser;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.FluentParser;
import net.quickwrite.fluent4j.parser.base.FluentElementParser;
import net.quickwrite.fluent4j.parser.pattern.FluentPatternParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

import java.util.ArrayList;
import java.util.List;

public final class FluentParserGroup implements FluentParser<FluentResource> {
    private final List<FluentElementParser<? extends FluentEntry>> baseParser = new ArrayList<>();

    public void addParser(final FluentElementParser<? extends FluentEntry> parser) {
        this.baseParser.add(parser);
    }

    public static FluentParserGroup getBasicParser() {
        final FluentParserGroup group = new FluentParserGroup();

        group.addParser(new WhitespaceSkipper());
        group.addParser(new CommentSkipper());

        group.addParser(new FluentTermParser(FluentPatternParser.DEFAULT_PARSER));
        group.addParser(new FluentMessageParser(FluentPatternParser.DEFAULT_PARSER));

        return group;
    }

    @Override
    public FluentResource parse(final ContentIterator iterator) {
        final List<FluentEntry> elements = new ArrayList<>();

        outer:
        while (iterator.line() != null) {
            final int[] position = iterator.position();

            for (final FluentElementParser<? extends FluentEntry> parser : baseParser) {
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
            throw new RuntimeException("Every parser returned FAILURE.");
        }

        return new FluentEntryResource(elements);
    }
}
