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

    private final PlaceableExpressionParser<? extends ResultBuilder> parser;

    DefaultPlaceableParser(final PlaceableExpressionParser<? extends ResultBuilder> parser) {
        this.parser = parser;
    }

    @SuppressWarnings("unchecked")
    public <B extends ResultBuilder> PlaceableExpressionParser<B> getParser() {
        return (PlaceableExpressionParser<B>) parser;
    }
}
