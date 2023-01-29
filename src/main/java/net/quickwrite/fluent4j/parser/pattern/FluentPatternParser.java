package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.parser.pattern.FluentPatternParserGroup;
import net.quickwrite.fluent4j.parser.FluentParser;

import java.util.List;

public interface FluentPatternParser extends FluentParser<List<FluentPattern>> {
    FluentPatternParser DEFAULT_PARSER = FluentPatternParserGroup.getBasicParser();
}
