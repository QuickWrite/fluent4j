package net.quickwrite.fluent4j.impl.parser;

import net.quickwrite.fluent4j.FluentResource;
import net.quickwrite.fluent4j.impl.parser.base.CommentParser;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.FluentResourceParser;
import net.quickwrite.fluent4j.parser.base.FluentBaseParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

import java.util.ArrayList;
import java.util.List;

public class FluentParserGroup implements FluentResourceParser {
    private final List<FluentBaseParser> baseParser = new ArrayList<>();

    public void addParser(final FluentBaseParser parser) {
        this.baseParser.add(parser);
    }

    public static FluentParserGroup getBasicParser() {
        final FluentParserGroup group = new FluentParserGroup();

        group.addParser(new CommentParser());

        // TODO: Add element parser

        return group;
    }

    @Override
    public FluentResource parse(final ContentIterator iterator) {
        final List<Object> elements = new ArrayList<>();

        outer:
        while (iterator.line() != null) {
            final int[] position = iterator.position();

            for (final FluentBaseParser parser : baseParser) {
                final ParseResult<?> result = parser.tryParse(iterator);

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

        return null;
    }
}