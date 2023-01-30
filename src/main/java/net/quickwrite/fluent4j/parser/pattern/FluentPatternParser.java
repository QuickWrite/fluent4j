package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.parser.pattern.FluentPlaceableParser;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.result.ParseResult;

public interface FluentPatternParser<T> {
    FluentPatternParser<FluentPattern> DEFAULT_PLACEABLE_PARSER = FluentPlaceableParser.getBasicParser();

    int getStartingChar();

    ParseResult<T> parse(final ContentIterator iterator, final FluentContentParser contentParser);
}
