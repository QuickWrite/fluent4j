package net.quickwrite.fluent4j.impl.parser.pattern.placeable;

import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;
import net.quickwrite.fluent4j.result.ResultBuilder;

public final class PlaceableParserList {
    private PlaceableParserList() {}

    public static final PlaceableExpressionParser<ResultBuilder>
            STRING = new FluentStringLiteralParser<>(),
            NUMBER = new FluentNumberLiteralParser<>(),
            FUNCTION = new FluentFunctionParser<>(),
            TERM_REFERENCE = new FluentTermReferenceParser<>(),
            MESSAGE_REFERENCE = new FluentMessageReferenceParser<>(),
            VARIABLE = new FluentVariableReferenceParser<>();
}