package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.FluentResolvable;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.container.exception.FluentPatternException;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.List;

public abstract class FluentBaseElement<I, B extends ResultBuilder> implements FluentResolvable<B> {
    protected final FluentIdentifier<I> identifier;
    protected final List<FluentPattern<B>> patterns;

    protected FluentBaseElement(final FluentIdentifier<I> identifier, final List<FluentPattern<B>> patterns) {
        this.identifier = identifier;
        this.patterns = patterns;
    }

    @Override
    public void resolve(final FluentScope<B> scope, final B builder) {
        if(!scope.addTraversed(identifier)) {
            // TODO: Better exception handling
            throw new RuntimeException("Recursive element found: '" + identifier.getFullIdentifier() + "'");
        }

        for (final FluentPattern<B> pattern : patterns) {
            try {
                pattern.resolve(scope, builder);
            } catch (final FluentPatternException exception) {
                exception.getDefaultDataWriter().write(builder);
            }
        }
    }
}
