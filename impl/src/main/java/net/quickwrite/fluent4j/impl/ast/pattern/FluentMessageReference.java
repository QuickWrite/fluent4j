package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.exception.FluentPatternException;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Optional;

public class FluentMessageReference<B extends ResultBuilder> implements FluentPlaceable<B> {
    protected final String identifier;

    public FluentMessageReference(final String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void resolve(final FluentScope<B> scope, final B builder) {
        try {
            unwrap(scope).resolve(scope, builder);
        } catch (final FluentPatternException exception) {
            exception.getDefaultDataWriter().write(builder);
        }
    }

    @Override
    public FluentPattern<B> unwrap(final FluentScope<B> scope) throws FluentPatternException {
        return scope.bundle().getMessage(identifier)
                .orElseThrow(
                        () -> FluentPatternException.getPlaceable(appender -> appender.append(identifier))
                );
    }

    @Override
    public String toSimpleString(final FluentScope<B> scope) {
        try {
            return unwrap(scope).toSimpleString(scope);
        } catch (final FluentPatternException e) {
            return "{" + identifier + "}";
        }
    }

    public static class AttributeReference<B extends ResultBuilder> extends FluentMessageReference<B> {
        private final String attributeIdentifier;

        public AttributeReference(final String identifier, final String attributeIdentifier) {
            super(identifier);

            this.attributeIdentifier = attributeIdentifier;
        }

        @Override
        public void resolve(final FluentScope<B> scope, final B builder) {
            final Optional<FluentEntry<B>> message = scope.bundle().getMessage(identifier);
            if (message.isEmpty()) {
                getException().getDefaultDataWriter().write(builder);
                return;
            }

            final Optional<FluentEntry.Attribute<B>> attribute = message.get().getAttribute(attributeIdentifier);
            if (attribute.isEmpty()) {
                getException().getDefaultDataWriter().write(builder);
                return;
            }

            attribute.get().resolve(scope, builder);
        }

        private FluentPatternException getException() {
            return FluentPatternException.getPlaceable(
                    appender -> appender.append(identifier).append('.').append(attributeIdentifier)
            );
        }
    }
}
