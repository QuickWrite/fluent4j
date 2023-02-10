package net.quickwrite.fluent4j.impl.parser.pattern.placeable;

import net.quickwrite.fluent4j.container.exception.FluentBuilderException;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentMessageReference;
import net.quickwrite.fluent4j.impl.util.ParserUtil;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableExpressionParser;
import net.quickwrite.fluent4j.parser.pattern.placeable.PlaceableParser;

import java.util.Optional;

public class FluentMessageReferenceParser implements PlaceableExpressionParser<FluentMessageReference>  {
    @Override
    public Optional<FluentMessageReference> parse(final ContentIterator iterator, final PlaceableParser placeableParser) {
        final Optional<String> identifier = ParserUtil.getIdentifier(iterator);
        if (identifier.isEmpty()) {
            return Optional.empty();
        }

        if (iterator.character() != '.') {
            return Optional.of(new FluentMessageReference(identifier.get()));
        }

        iterator.nextChar();

        return Optional.of(
                new FluentMessageReference.AttributeReference(
                        identifier.get(),
                        ParserUtil.getIdentifier(iterator).orElseThrow(
                                () -> new FluentBuilderException("Expected attribute name", iterator)
                        )
                )
        );
    }
}
