package net.quickwrite.fluent4j.container;

import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Set;

public interface FluentScope<B extends ResultBuilder> extends Cloneable {
    FluentBundle<B> bundle();

    Set<FluentIdentifier<?>> traversed();

    boolean addTraversed(final FluentIdentifier<?> key);

    ArgumentList<B> arguments();

    void setArguments(final ArgumentList<B> arguments);

    B builder();

    FluentScope<B> clone();
}
