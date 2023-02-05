package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.container.FluentScope;

import java.io.IOException;

public interface FluentResolvable {
    void resolve(final FluentScope scope, final Appendable appendable) throws IOException;
}
