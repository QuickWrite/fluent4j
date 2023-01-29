package net.quickwrite.fluent4j.impl.parser;

import net.quickwrite.fluent4j.ast.FluentEntry;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.impl.container.FluentEntryResource;
import net.quickwrite.fluent4j.impl.parser.base.CommentSkipper;
import net.quickwrite.fluent4j.impl.parser.base.WhitespaceSkipper;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.FluentResourceParser;
import net.quickwrite.fluent4j.parser.base.FluentParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

import java.util.ArrayList;
import java.util.List;

public final class FluentParserGroup implements FluentResourceParser {
    private final List<FluentParser<? extends FluentEntry>> baseParser = new ArrayList<>();

    public void addParser(final FluentParser<? extends FluentEntry> parser) {
        this.baseParser.add(parser);
    }

    public static FluentParserGroup getBasicParser() {
        final FluentParserGroup group = new FluentParserGroup();

        group.addParser(new WhitespaceSkipper());
        group.addParser(new CommentSkipper());

        // TODO: Add element parser

        return group;
    }

    @Override
    public FluentResource parse(final ContentIterator iterator) {
        final List<FluentEntry> elements = new ArrayList<>();

        outer:
        while (iterator.line() != null) {
            final int[] position = iterator.position();

            for (final FluentParser<? extends FluentEntry> parser : baseParser) {
                final ParseResult<? extends FluentEntry> result = parser.tryParse(iterator);

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
