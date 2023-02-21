package net.quickwrite.fluent4j.impl.parser.pattern.placeable;

import net.quickwrite.fluent4j.container.exception.FluentExpectedException;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentVariableReference;
import net.quickwrite.fluent4j.impl.util.ParserUtil;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableParser;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Optional;

public class FluentVariableReferenceParser<B extends ResultBuilder> implements PlaceableExpressionParser<FluentVariableReference<B>, B> {
    @Override
    public Optional<FluentVariableReference<B>> parse(final ContentIterator iterator, final PlaceableParser<B> placeableParser) {
        if (iterator.character() != '$') {
            return Optional.empty();
        }

        iterator.nextChar();

        final Optional<String> identifier = ParserUtil.getIdentifier(iterator);

        if (identifier.isEmpty()) {
            throw new FluentExpectedException("identifier", Character.toString(iterator.character()), iterator);
        }

        return Optional.of(new FluentVariableReference<>(identifier.get()));
    }
}
