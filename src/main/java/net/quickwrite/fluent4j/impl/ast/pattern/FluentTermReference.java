package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.container.exception.FluentPatternException;
import net.quickwrite.fluent4j.impl.ast.entry.FluentTermElement;
import net.quickwrite.fluent4j.impl.ast.pattern.container.cache.FluentCachedChecker;
import net.quickwrite.fluent4j.impl.container.FluentResolverScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Optional;
import java.util.function.Function;

public class FluentTermReference<B extends ResultBuilder> extends ParameterizedLiteral<String, B> {
    public FluentTermReference(final String identifier, final ArgumentList<B> argumentList) {
        super(identifier, argumentList);
    }

    @Override
    public void resolve(final FluentScope<B> scope, final B builder) {
        unwrap(scope).resolve(new FluentResolverScope<>(scope.bundle(), argumentList, scope.traversed(), scope.builder()), builder);
    }

    @SuppressWarnings("unchecked")
    @Override
    public FluentPattern<B> unwrap(final FluentScope<B> scope) {
        final Optional<FluentTermElement> entry = scope.bundle().getEntry(identifier, FluentTermElement.class);
        if (entry.isEmpty()) {
            throw FluentPatternException.getPlaceable(appender -> appender.append('-').append(identifier));
        }

        return entry.get();
    }

    @Override
    public String toSimpleString(final FluentScope<B> scope) {
        return unwrap(scope).toSimpleString(scope);
    }

    public static class AttributeReference<B extends ResultBuilder> extends FluentTermReference<B> implements FluentPlaceable.CannotPlaceable, FluentSelect.Selectable<B> {
        private final String attributeIdentifier;

        public AttributeReference(final String identifier, final String attributeIdentifier, final ArgumentList<B> argumentList) {
            super(identifier, argumentList);

            this.attributeIdentifier = attributeIdentifier;
        }

        @Override
        public void resolve(final FluentScope<B> scope, final B builder) {
            final FluentEntry.Attribute<B> attribute = getAttribute(scope);

            attribute.resolve(scope, builder);
        }

        @Override
        public String getName() {
            return "Term Attribute";
        }

        @SuppressWarnings("unchecked")
        private FluentEntry.Attribute<B> getAttribute(final FluentScope<B> scope) {
            // TODO: Don't just throw
            final Optional<FluentTermElement> termElement = scope.bundle()
                    .getEntry(identifier, FluentTermElement.class);

            if (termElement.isEmpty()) {
                throw getException();
            }

            final Optional<AttributeReference> attribute = termElement.get().getAttribute(attributeIdentifier);

            if (attribute.isEmpty()) {
                throw getException();
            }

            return (FluentEntry.Attribute<B>) attribute.get();
        }

        private FluentPatternException getException() {
            return FluentPatternException.getPlaceable(
                    builder -> builder.append('-').append(identifier).append('.').append(attributeIdentifier)
            );
        }

        @Override
        public Function<FluentSelect.FluentVariant<B>, Boolean> selectChecker(final FluentScope<B> scope) {
            final FluentEntry.Attribute<B> attribute = getAttribute(scope);
            if (attribute.getPatterns().size() != 1) {
                // Returns null to jump to the default directly
                return null;
            }

            return new FluentCachedChecker<>(scope, attribute);
        }
    }
}
