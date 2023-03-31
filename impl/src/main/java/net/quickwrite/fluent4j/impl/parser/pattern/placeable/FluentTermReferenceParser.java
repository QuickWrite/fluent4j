package net.quickwrite.fluent4j.impl.parser.pattern.placeable;

import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.exception.FluentBuilderException;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentTermReference;
import net.quickwrite.fluent4j.impl.util.ParserUtil;
import net.quickwrite.fluent4j.iterator.ContentIterator;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

public class FluentTermReferenceParser<B extends ResultBuilder> extends ParameterizedLiteralParser<Map.Entry<String, String>, B> {
    @Override
    protected Optional<Map.Entry<String, String>> parseIdentifier(final ContentIterator iterator) {
        if (iterator.character() != '-') {
            return Optional.empty();
        }

        iterator.nextChar();

        final Optional<String> identifier = ParserUtil.getIdentifier(iterator);
        if (identifier.isEmpty()) {
            return Optional.empty();
        }

        if (iterator.character() == '.') {
            iterator.nextChar();

            return Optional.of(
                    new AbstractMap.SimpleImmutableEntry<>(
                            identifier.get(),
                            ParserUtil.getIdentifier(iterator).orElseThrow(
                                    () -> new FluentBuilderException("Expected attribute name", iterator)
                            )
                    )
            );
        }

        return Optional.of(new AbstractMap.SimpleImmutableEntry<>(identifier.get(), null));
    }

    @Override
    protected FluentPlaceable<B> getInstance(final Map.Entry<String, String> identifier) {
        return getInstance(identifier, ArgumentList.empty());
    }

    @Override
    protected FluentPlaceable<B> getInstance(final Map.Entry<String, String> identifier, final ArgumentList<B> argumentList) {
        if (identifier.getValue() != null) {
            return new FluentTermReference.AttributeReference<>(identifier.getKey(), identifier.getValue(), argumentList);
        }

        return new FluentTermReference<>(identifier.getKey(), argumentList);
    }

    @Override
    protected boolean optionalArguments() {
        return true;
    }
}
