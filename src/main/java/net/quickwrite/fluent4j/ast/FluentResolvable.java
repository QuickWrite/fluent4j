package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.container.FluentScope;

import java.io.IOException;

public interface FluentResolvable {
    void resolve(final FluentScope scope, final Appendable appendable) throws IOException;

    default String toSimpleString(final FluentScope scope) throws IOException {
        final StringBuilder builder = new StringBuilder();

        this.resolve(scope, builder);

        return builder.toString();
    }
}
