package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.impl.parser.pattern.placeable.*;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;
import net.quickwrite.fluent4j.result.ResultBuilder;

public enum DefaultPlaceableParser implements PlaceableExpressionParser.PlaceableExpressionParserList {
    STRING(PlaceableParserList.STRING),
    NUMBER(PlaceableParserList.NUMBER),
    FUNCTION(PlaceableParserList.FUNCTION),
    TERM_REFERENCE(PlaceableParserList.TERM_REFERENCE),
    MESSAGE_REFERENCE(PlaceableParserList.MESSAGE_REFERENCE),
    VARIABLE(PlaceableParserList.VARIABLE);

    private final PlaceableExpressionParser parser;

    DefaultPlaceableParser(final PlaceableExpressionParser parser) {
        this.parser = parser;
    }

    public PlaceableExpressionParser getParser() {
        return parser;
    }
}
