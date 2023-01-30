package net.quickwrite.fluent4j.parser.pattern.placeable;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.parser.base.FluentElementParser;

public interface PlaceableExpressionParser<T extends FluentPattern> extends FluentElementParser<T> {
    boolean canSelectExpression();
}
