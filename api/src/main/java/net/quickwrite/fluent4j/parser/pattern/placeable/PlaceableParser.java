package net.quickwrite.fluent4j.parser.pattern.placeable;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.FluentPatternParser;

import java.util.Optional;

/**
 * The raw interface for the parser that parses
 * any type of Placeable and returns the correct implementation
 * of that Placeable.
 *
 * <p>
 *     So the Parser would parse {@code { 5 }} and would output
 *     a NumberLiteral and if it would parse {@code { "Hello" }}
 *     it would return a StringLiteral.
 * </p>
 */
public interface PlaceableParser extends FluentPatternParser<FluentPattern> {
    Optional<FluentPlaceable> parsePlaceable(final ContentIterator iterator);

    interface Builder extends net.quickwrite.fluent4j.util.Builder<PlaceableParser> {
        Builder addParser(final PlaceableExpressionParser parser);

        default Builder addParser(final PlaceableExpressionParser.PlaceableExpressionParserList parser) {
            return addParser(parser.getParser());
        }
    }
}
