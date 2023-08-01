package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.entry.FluentAttributeEntry;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.exception.FluentPatternException;
import net.quickwrite.fluent4j.impl.ast.entry.FluentTermElement;
import net.quickwrite.fluent4j.impl.ast.pattern.container.cache.FluentCachedChecker;
import net.quickwrite.fluent4j.impl.util.ErrorUtil;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Optional;

public class FluentTermReference<B extends ResultBuilder> extends ParameterizedLiteral<String, B> {
    public FluentTermReference(final String identifier, final ArgumentList<B> argumentList) {
        super(identifier, argumentList);
    }

    @Override
    public void resolve(final FluentScope<B> scope, final B builder) {
        final FluentScope<B> clonedScope = scope.clone();
        clonedScope.setArguments(argumentList);
        try {
            unwrap(scope).resolve(clonedScope, builder);
        } catch (final FluentPatternException exception) {
            exception.getDataWriter().write(builder);
        }
    }

    @Override
    public FluentPattern<B> unwrap(final FluentScope<B> scope) throws FluentPatternException {
        final Optional<FluentTermElement<B>> entry = scope.bundle().getEntry(identifier, FluentTermElement.class);
        if (entry.isEmpty()) {
            throw ErrorUtil.getPlaceablePatternException(appender -> appender.append('-').append(identifier));
        }

        return entry.get();
    }

    @Override
    public String toSimpleString(final FluentScope<B> scope) {
        try {
            return unwrap(scope).toSimpleString(scope);
        } catch (final FluentPatternException e) {
            return "{-" + identifier + "}";
        }
    }

    public static class AttributeReference<B extends ResultBuilder> extends FluentTermReference<B> implements FluentPlaceable.CannotPlaceable, FluentSelect.Selectable<B> {
        private final String attributeIdentifier;

        public AttributeReference(final String identifier, final String attributeIdentifier, final ArgumentList<B> argumentList) {
            super(identifier, argumentList);

            this.attributeIdentifier = attributeIdentifier;
        }

        @Override
        public void resolve(final FluentScope<B> scope, final B builder) {
            final Optional<FluentAttributeEntry.Attribute<B>> attribute = getAttribute(scope);
            if (attribute.isEmpty()) {
                getException().getDataWriter().write(builder);
                return;
            }

            attribute.get().resolve(scope, builder);
        }

        @Override
        public String getName() {
            return "Term Attribute";
        }

        private Optional<FluentAttributeEntry.Attribute<B>> getAttribute(final FluentScope<B> scope) {
            final Optional<FluentTermElement<B>> termElement = scope.bundle()
                    .getEntry(identifier, FluentTermElement.class);

            if (termElement.isEmpty()) {
                return Optional.empty();
            }

            return termElement.get().getAttribute(attributeIdentifier);
        }

        private FluentPatternException getException() {
            return ErrorUtil.getPlaceablePatternException(
                    builder -> builder.append('-').append(identifier).append('.').append(attributeIdentifier)
            );
        }

        @Override
        public SelectChecker<B> selectChecker(final FluentScope<B> scope) {
            final Optional<FluentAttributeEntry.Attribute<B>> attribute = getAttribute(scope);

            if (attribute.isEmpty() || !attribute.get().isSelectable()) {
                // Returns null to jump to the default directly
                return null;
            }

            return new FluentCachedChecker<>(scope, attribute.get());
        }
    }
}
