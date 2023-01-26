package net.quickwrite.fluent4j.impl.parser;

import net.quickwrite.fluent4j.FluentResource;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.FluentResourceParser;
import net.quickwrite.fluent4j.parser.base.FluentBaseParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class FluentParserGroup implements FluentResourceParser {
    private final List<FluentBaseParser> baseParser = new ArrayList<>();

    public void addParser(final FluentBaseParser parser) {
        this.baseParser.add(parser);
    }

    public static FluentParserGroup getBasicParser() {
        final FluentParserGroup group = new FluentParserGroup();

        group.addParser(iterator -> {
            // TODO: Make this parser more fluent compliant
            if (iterator.character() != '#') {
                return ParseResult.failure();
            }

            iterator.nextLine();

            return ParseResult.skip();
        });

        // TODO: Add element parser

        return group;
    }

    @Override
    public FluentResource parse(final ContentIterator iterator) {
        final List<Object> elements = new ArrayList<>();

        back:
        if (iterator.line() != null) {
            final int[] position = iterator.position();

            for(final FluentBaseParser parser : baseParser) {
                final ParseResult<?> result = parser.tryParse(iterator);

                if (result.getType() == ParseResult.ParseResultType.SUCCESS) {
                    elements.add(result.getValue());
                    break back;
                }
                if (result.getType() == ParseResult.ParseResultType.SKIP) {
                    break back;
                }

                iterator.setPosition(position);
            }

            // TODO: Something went wrong and it must be handled
        }

        return null;
    }
}
