package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.parser.pattern.FluentContentParserGroup;
import net.quickwrite.fluent4j.parser.FluentParser;

import java.util.List;

public interface FluentContentParser extends FluentParser<List<FluentPattern>> {
    FluentContentParser DEFAULT_PARSER = FluentContentParserGroup.getBasicParser();
}
