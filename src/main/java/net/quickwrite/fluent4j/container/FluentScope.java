package net.quickwrite.fluent4j.container;

import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;

import java.util.Set;

public interface FluentScope {
    FluentBundle getBundle();

    Set<FluentIdentifier<?>> getTraversed();

    boolean addTraversed(final FluentIdentifier<?> key);

    ArgumentList getArguments();
}
