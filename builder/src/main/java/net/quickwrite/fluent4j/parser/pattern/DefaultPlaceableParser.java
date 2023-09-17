package net.quickwrite.fluent4j.parser.pattern;

import net.quickwrite.fluent4j.impl.parser.pattern.placeable.*;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;

public enum DefaultPlaceableParser implements PlaceableExpressionParser.PlaceableExpressionParserList {
    STRING(new FluentStringLiteralParser()),
    NUMBER(new FluentNumberLiteralParser()),
    FUNCTION(new FluentFunctionParser()),
    TERM_REFERENCE(new FluentTermReferenceParser()),
    MESSAGE_REFERENCE(new FluentMessageReferenceParser()),
    VARIABLE(new FluentVariableReferenceParser());

    private final PlaceableExpressionParser parser;

    DefaultPlaceableParser(final PlaceableExpressionParser parser) {
        this.parser = parser;
    }

    public PlaceableExpressionParser getParser() {
        return parser;
    }
}
