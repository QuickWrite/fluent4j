package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.entry.FluentAttributeEntry;
import net.quickwrite.fluent4j.ast.entry.FluentMessage;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.exception.FluentPatternException;
import net.quickwrite.fluent4j.impl.util.ErrorUtil;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Optional;

public class FluentMessageReference implements FluentPlaceable {
    protected final String identifier;

    public FluentMessageReference(final String identifier) {
        this.identifier = identifier;
    }

    @Override
    public void resolve(final FluentScope scope, final ResultBuilder builder) {
        try {
            unwrap(scope).resolve(scope, builder);
        } catch (final FluentPatternException exception) {
            exception.getDataWriter().write(builder);
        }
    }

    @Override
    public FluentPattern unwrap(final FluentScope scope) throws FluentPatternException {
        return scope.bundle().getMessage(identifier)
                .orElseThrow(
                        () -> ErrorUtil.getPlaceablePatternException(identifier)
                );
    }

    @Override
    public String toSimpleString(final FluentScope scope) {
        try {
            return unwrap(scope).toSimpleString(scope);
        } catch (final FluentPatternException e) {
            return "{" + identifier + "}";
        }
    }

    public static class AttributeReference extends FluentMessageReference {
        private final String attributeIdentifier;

        public AttributeReference(final String identifier, final String attributeIdentifier) {
            super(identifier);

            this.attributeIdentifier = attributeIdentifier;
        }

        @Override
        public void resolve(final FluentScope scope, final ResultBuilder builder) {
            final Optional<FluentMessage> message = scope.bundle().getMessage(identifier);
            if (message.isEmpty()) {
                getException().getDataWriter().write(builder);
                return;
            }

            final Optional<FluentAttributeEntry.Attribute> attribute = message.get().getAttribute(attributeIdentifier);
            if (attribute.isEmpty()) {
                getException().getDataWriter().write(builder);
                return;
            }

            attribute.get().resolve(scope, builder);
        }

        private FluentPatternException getException() {
            return ErrorUtil.getPlaceablePatternException(
                    appender -> appender.append(identifier).append('.').append(attributeIdentifier)
            );
        }
    }
}
