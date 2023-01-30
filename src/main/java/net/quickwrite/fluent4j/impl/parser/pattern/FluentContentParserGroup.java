package net.quickwrite.fluent4j.impl.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.FluentContentParser;
import net.quickwrite.fluent4j.parser.pattern.FluentPatternParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

import java.util.ArrayList;
import java.util.List;

public class FluentContentParserGroup implements FluentContentParser {
    private final List<FluentPatternParser<? extends FluentPattern>> patternParserList = new ArrayList<>();

    public static FluentContentParserGroup getBasicParser() {
        final FluentContentParserGroup group = new FluentContentParserGroup();

        return group;
    }

    public void addParser(final FluentPatternParser<? extends FluentPattern> parser) {
        this.patternParserList.add(parser);
    }

    @Override
    public List<FluentPattern> parse(final ContentIterator iterator) {
        return null;
    }
}
