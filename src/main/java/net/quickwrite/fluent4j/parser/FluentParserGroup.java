package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.FluentResource;
import net.quickwrite.fluent4j.parser.base.FluentBaseParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;
import net.quickwrite.fluent4j.stream.ContentStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FluentParserGroup implements FluentResourceParser {
    private final List<FluentBaseParser> baseParser = new ArrayList<>();

    public void addParser(final FluentBaseParser parser) {
        this.baseParser.add(parser);
    }

    static FluentParserGroup getBasicParser() {
        final FluentParserGroup group = new FluentParserGroup();

        group.addParser(content -> {
            // TODO: Make this parser more fluent compliant
            if (content.next() != '#') {
                return ParseResult.failure();
            }

            content.getLine();

            return ParseResult.skip();
        });

        // TODO: Add element parser

        return group;
    }

    @Override
    public FluentResource parse(final ContentStream stream) {
        final List<Object> elements = new ArrayList<>();
        while (stream.hasNext()) {
            final long position = stream.getPosition();

            for(final FluentBaseParser parser : baseParser) {
                final ParseResult<?> result = parser.tryParse(stream);

                if (result.getType() == ParseResult.ParseResultType.SUCESS) {
                    elements.add(result.getValue());
                    break;
                }
                if (result.getType() == ParseResult.ParseResultType.SKIP) {
                    break;
                }

                stream.setPosition(position);
            }
        }

        return null;
    }
}
