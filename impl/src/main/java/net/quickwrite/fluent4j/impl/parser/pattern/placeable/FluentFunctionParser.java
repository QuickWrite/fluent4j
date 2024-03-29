package net.quickwrite.fluent4j.impl.parser.pattern.placeable;

import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentFunctionReference;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Optional;

public class FluentFunctionParser extends ParameterizedLiteralParser<String, ResultBuilder> {
    @Override
    protected Optional<String> parseIdentifier(final ContentIterator iterator) {
        if (!isFunctionIdentifierStart(iterator.character())) {
            return Optional.empty();
        }

        final int position = iterator.position()[1];

        /* @formatter:off */
        while (isFunctionIdentifierPart(iterator.nextChar()));
        /* @formatter:on */

        return Optional.of(iterator.line().substring(position, iterator.position()[1]));
    }

    private boolean isFunctionIdentifierStart(final int character) {
        return character >= 'A' && character <= 'Z';
    }

    private boolean isFunctionIdentifierPart(final int character) {
        return isFunctionIdentifierStart(character)
                || character >= '0' && character <= '9'
                || character == '-'
                || character == '_';
    }

    @Override
    protected FluentPlaceable getInstance(final String identifier) {
        return getInstance(identifier, ArgumentList.empty());
    }

    @Override
    protected FluentPlaceable getInstance(final String identifier, final ArgumentList argumentList) {
        return new FluentFunctionReference(identifier, argumentList);
    }

    @Override
    protected boolean optionalArguments() {
        return false;
    }
}
