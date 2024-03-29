package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.entry.FluentAttributeEntry;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.exception.FluentPatternException;
import net.quickwrite.fluent4j.impl.ast.entry.FluentTermElement;
import net.quickwrite.fluent4j.impl.util.ErrorUtil;
import net.quickwrite.fluent4j.impl.util.FluentCheckerUtil;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Optional;

public class FluentTermReference extends ParameterizedLiteral<String> {
    public FluentTermReference(final String identifier, final ArgumentList argumentList) {
        super(identifier, argumentList);
    }

    @Override
    public void resolve(final FluentScope scope, final ResultBuilder builder) {
        final FluentScope clonedScope = scope.clone();
        clonedScope.setArguments(argumentList);
        try {
            unwrap(scope).resolve(clonedScope, builder);
        } catch (final FluentPatternException exception) {
            exception.getDataWriter().write(builder);
        }
    }

    @Override
    public FluentPattern unwrap(final FluentScope scope) throws FluentPatternException {
        final Optional<FluentTermElement> entry = scope.bundle().getEntry(identifier, FluentTermElement.class);
        if (entry.isEmpty()) {
            throw ErrorUtil.getPlaceablePatternException(appender -> appender.append('-').append(identifier));
        }

        return entry.get();
    }

    @Override
    public String toSimpleString(final FluentScope scope) {
        try {
            return unwrap(scope).toSimpleString(scope);
        } catch (final FluentPatternException e) {
            return "{-" + identifier + "}";
        }
    }

    public static class AttributeReference extends FluentTermReference implements FluentPlaceable.CannotPlaceable, FluentSelect.Selectable {
        private final String attributeIdentifier;

        public AttributeReference(final String identifier, final String attributeIdentifier, final ArgumentList argumentList) {
            super(identifier, argumentList);

            this.attributeIdentifier = attributeIdentifier;
        }

        @Override
        public void resolve(final FluentScope scope, final ResultBuilder builder) {
            final Optional<FluentAttributeEntry.Attribute> attribute = getAttribute(scope);
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

        private Optional<FluentAttributeEntry.Attribute> getAttribute(final FluentScope scope) {
            final Optional<FluentTermElement> termElement = scope.bundle()
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
        public FluentSelect.FluentVariant select(final FluentScope scope,
                                                 final FluentSelect.FluentVariant[] variants,
                                                 final FluentSelect.FluentVariant defaultVariant
        ) {
            final Optional<FluentAttributeEntry.Attribute> attribute = getAttribute(scope);

            if (attribute.isEmpty() || !attribute.get().isSelectable()) {
                return defaultVariant;
            }

            return FluentCheckerUtil.check(scope, attribute.get(), variants, defaultVariant);
        }
    }
}
