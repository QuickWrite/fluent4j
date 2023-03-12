package net.quickwrite.fluent4j.parser.pattern.placeable;

import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.impl.parser.pattern.placeable.*;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Optional;

public interface PlaceableExpressionParser<B extends ResultBuilder> {
    Optional<FluentPlaceable<B>> parse(final ContentIterator iterator, final PlaceableParser<B> placeableParser);

    enum DefaultParser {
        STRING(new FluentStringLiteralParser<>()),
        NUMBER(new FluentNumberLiteralParser<>()),
        FUNCTION(new FluentFunctionParser<>()),
        TERM_REFERENCE(new FluentTermReferenceParser<>()),
        MESSAGE_REFERENCE(new FluentMessageReferenceParser<>()),
        VARIABLE(new FluentVariableReferenceParser<>());

        private final PlaceableExpressionParser<? extends ResultBuilder> parser;

        DefaultParser(final PlaceableExpressionParser<? extends ResultBuilder> parser) {
            this.parser = parser;
        }

        @SuppressWarnings("unchecked")
        public <B extends ResultBuilder> PlaceableExpressionParser<B> getParser() {
            return (PlaceableExpressionParser<B>) parser;
        }
    }
}
