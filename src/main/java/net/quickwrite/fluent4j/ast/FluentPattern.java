package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.container.FluentScope;

public interface FluentPattern extends FluentResolvable {
    FluentPattern unwrap(final FluentScope scope);

    interface Stringable {
        String getAsString();
    }
}
