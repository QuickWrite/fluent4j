package net.quickwrite.fluent4j.impl.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.base.FluentElementParser;
import net.quickwrite.fluent4j.parser.pattern.FluentPatternParser;

import java.util.ArrayList;
import java.util.List;

public class FluentPatternParserGroup implements FluentPatternParser {
    private final List<FluentElementParser<? extends FluentPattern>> patternParser = new ArrayList<>();

    public static FluentPatternParserGroup getBasicParser() {
        final FluentPatternParserGroup group = new FluentPatternParserGroup();

        return group;
    }

    public void addParser(final FluentElementParser<? extends FluentPattern> parser) {
        this.patternParser.add(parser);
    }

    @Override
    public List<FluentPattern> parse(final ContentIterator iterator) {
        return null;
    }
}
