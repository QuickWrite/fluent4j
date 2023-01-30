package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.parser.pattern.FluentContentParserGroup;
import net.quickwrite.fluent4j.iterator.ContentIterator;

import java.util.List;
import java.util.function.Function;

public interface FluentContentParser {
    FluentContentParser DEFAULT_PARSER = FluentContentParserGroup.getBasicParser();

    List<FluentPattern> parse(final ContentIterator iterator, final Function<ContentIterator, Boolean> endChecker);
}
