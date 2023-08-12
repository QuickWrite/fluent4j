package net.quickwrite.fluent4j.impl.ast.entry;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.FluentResolvable;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.List;

public abstract class FluentBaseElement<I> implements FluentResolvable {
    protected final FluentIdentifier<I> identifier;
    protected final List<FluentPattern> patterns;

    protected FluentBaseElement(final FluentIdentifier<I> identifier, final List<FluentPattern> patterns) {
        this.identifier = identifier;
        this.patterns = patterns;
    }

    @Override
    public void resolve(final FluentScope scope, final ResultBuilder builder) {
        if(!scope.addTraversed(identifier)) {
            builder.append("{???}");
            return;
        }

        for (final FluentPattern pattern : patterns) {
            pattern.resolve(scope, builder);
        }
    }
}
