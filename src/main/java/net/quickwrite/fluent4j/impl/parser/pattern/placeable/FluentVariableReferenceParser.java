package net.quickwrite.fluent4j.impl.parser.pattern.placeable;

import net.quickwrite.fluent4j.impl.ast.pattern.FluentVariableReference;
import net.quickwrite.fluent4j.impl.util.ParserUtil;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;
import net.quickwrite.fluent4j.parser.result.ParseResult;

import java.util.Optional;

public class FluentVariableReferenceParser implements PlaceableExpressionParser<FluentVariableReference> {
    @Override
    public ParseResult<FluentVariableReference> parse(final ContentIterator iterator) {
        if (iterator.character() != '$') {
            return ParseResult.failure();
        }

        iterator.nextChar();

        final Optional<String> identifier = ParserUtil.getIdentifier(iterator);

        if (identifier.isEmpty()) {
            throw new RuntimeException("Expected identifier, but got '" + Character.toString(iterator.character()) + "'");
        }

        return ParseResult.success(new FluentVariableReference(identifier.get()));
    }

    @Override
    public boolean canSelectExpression() {
        return true;
    }
}
