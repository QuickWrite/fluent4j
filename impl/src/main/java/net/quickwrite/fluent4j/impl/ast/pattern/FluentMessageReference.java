package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.exception.FluentPatternException;
import net.quickwrite.fluent4j.result.ResultBuilder;

public class FluentMessageReference<B extends ResultBuilder> implements FluentPlaceable<B> {
    protected final String identifier;

    public FluentMessageReference(final String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void resolve(final FluentScope<B> scope, final B builder) {
        unwrap(scope).resolve(scope, builder);
    }

    @Override
    public FluentPattern<B> unwrap(final FluentScope<B> scope) {
        return scope.bundle().getMessage(identifier)
                .orElseThrow(
                        () -> FluentPatternException.getPlaceable(appender -> appender.append(identifier))
                );
    }

    @Override
    public String toSimpleString(final FluentScope<B> scope) {
        return unwrap(scope).toSimpleString(scope);
    }

    public static class AttributeReference<B extends ResultBuilder> extends FluentMessageReference<B> {
        private final String attributeIdentifier;

        public AttributeReference(final String identifier, final String attributeIdentifier) {
            super(identifier);

            this.attributeIdentifier = attributeIdentifier;
        }

        @Override
        public void resolve(final FluentScope<B> scope, final B builder) {
            // TODO: Don't just throw
            final FluentEntry<B> message = scope.bundle().getMessage(identifier).orElseThrow(this::getException);

            final FluentEntry.Attribute<B> attribute = message.getAttribute(attributeIdentifier).orElseThrow(this::getException);

            attribute.resolve(scope, builder);
        }

        private FluentPatternException getException() {
            return FluentPatternException.getPlaceable(
                    appender -> appender.append(identifier).append('.').append(attributeIdentifier)
            );
        }
    }
}
