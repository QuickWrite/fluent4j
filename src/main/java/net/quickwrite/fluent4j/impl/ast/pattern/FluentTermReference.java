package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.container.exception.FluentPatternException;
import net.quickwrite.fluent4j.impl.ast.entry.FluentTerm;
import net.quickwrite.fluent4j.impl.ast.pattern.container.cache.FluentCachedChecker;
import net.quickwrite.fluent4j.impl.container.FluentResolverScope;

import java.io.IOException;
import java.util.function.Function;

public class FluentTermReference extends ParameterizedLiteral<String> {
    public FluentTermReference(final String identifier, final ArgumentList argumentList) {
        super(identifier, argumentList);
    }

    @Override
    public void resolve(final FluentScope scope, final Appendable appendable) throws IOException {
        // TODO: Don't just throw
        final FluentTerm term = scope.getBundle().getEntry(identifier, FluentTerm.class)
                .orElseThrow(
                        () -> FluentPatternException.getPlaceable(appender -> appender.append('-').append(identifier))
                );

        term.resolve(new FluentResolverScope(scope.getBundle(), argumentList, scope.getTraversed()), appendable);
    }

    @Override
    public FluentPattern unwrap(final FluentScope scope) {
        return this;
    }

    public static class AttributeReference extends FluentTermReference implements FluentPlaceable.CannotPlaceable, FluentSelect.Selectable {
        private final String attributeIdentifier;

        public AttributeReference(final String identifier, final String attributeIdentifier, final ArgumentList argumentList) {
            super(identifier, argumentList);

            this.attributeIdentifier = attributeIdentifier;
        }

        @Override
        public void resolve(final FluentScope scope, final Appendable appendable) throws IOException {
            final FluentEntry.Attribute attribute = getAttribute(scope);

            attribute.resolve(scope, appendable);
        }

        @Override
        public String getName() {
            return "Term Attribute";
        }

        private FluentEntry.Attribute getAttribute(final FluentScope scope) {
            // TODO: Don't just throw
            final FluentTerm term = scope.getBundle().getEntry(identifier, FluentTerm.class).orElseThrow(this::getException);

            return term.getAttribute(attributeIdentifier).orElseThrow(this::getException);
        }

        private FluentPatternException getException() {
            return FluentPatternException.getPlaceable(
                    appender -> appender.append('-').append(identifier).append('.').append(attributeIdentifier)
            );
        }

        @Override
        public Function<FluentSelect.FluentVariant, Boolean> selectChecker(final FluentScope scope) {
            final FluentEntry.Attribute attribute = getAttribute(scope);
            if (attribute.getPatterns().size() != 1) {
                return (variant) -> false;
            }

            return new FluentCachedChecker(scope, attribute);
        }
    }
}
