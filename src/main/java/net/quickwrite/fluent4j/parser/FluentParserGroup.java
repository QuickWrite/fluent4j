package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.FluentResource;
import net.quickwrite.fluent4j.parser.base.FluentBaseParser;
import net.quickwrite.fluent4j.stream.ContentStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FluentParserGroup implements FluentResourceParser {
    private final List<FluentBaseParser> baseParser = new ArrayList<>();

    public void addParser(final FluentBaseParser parser) {
        this.baseParser.add(parser);
    }

    @Override
    public FluentResource parse(final ContentStream stream) {
        final List<Object> elements = new ArrayList<>();
        while (stream.hasNext()) {
            final long position = stream.getPosition();

            for(final FluentBaseParser parser : baseParser) {
                final Optional<?> element = parser.tryParse(stream);

                if (element.isPresent()) {
                    elements.add(element.get());
                    break;
                }

                stream.setPosition(position);
            }
        }

        return null;
    }
}
