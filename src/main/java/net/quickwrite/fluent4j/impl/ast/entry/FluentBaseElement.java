package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.FluentResolvable;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.container.FluentScope;

import java.io.IOException;
import java.util.List;

public abstract class FluentBaseElement<I> implements FluentResolvable {
    protected final FluentIdentifier<I> identifier;
    protected final List<FluentPattern> patterns;

    protected FluentBaseElement(final FluentIdentifier<I> identifier, final List<FluentPattern> patterns) {
        this.identifier = identifier;
        this.patterns = patterns;
    }

    @Override
    public void resolve(final FluentScope scope, final Appendable appendable) throws IOException {
        if(!scope.addTraversed(identifier)) {
            // TODO: Better exception handling
            throw new RuntimeException("Recursive element found: '" + identifier.getFullIdentifier() + "'");
        }

        for (final FluentPattern pattern : patterns) {
            pattern.resolve(scope, appendable);
        }
    }
}
