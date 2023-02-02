package net.quickwrite.fluent4j.impl.parser.pattern.placeable;

import net.quickwrite.fluent4j.ast.pattern.AttributeList;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentTermReference;
import net.quickwrite.fluent4j.impl.util.ParserUtil;
import net.quickwrite.fluent4j.iterator.ContentIterator;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;

public class FluentTermReferenceParser extends ParameterizedLiteralParser<FluentTermReference, Map.Entry<String, String>> {
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
                                    () -> new RuntimeException("Expected attribute name")
                            )
                    )
            );
        }

        return Optional.of(new AbstractMap.SimpleImmutableEntry<>(identifier.get(), null));
    }

    @Override
    protected FluentTermReference getInstance(final Map.Entry<String, String> identifier) {
        return getInstance(identifier, AttributeList.EMPTY);
    }

    @Override
    protected FluentTermReference getInstance(final Map.Entry<String, String> identifier, final AttributeList attributes) {
        if (identifier.getValue() != null) {
            return new FluentTermReference.AttributeReference(identifier.getKey(), identifier.getValue(), attributes);
        }

        return new FluentTermReference(identifier.getKey(), attributes);
    }

    @Override
    protected boolean optionalArguments() {
        return true;
    }
}
