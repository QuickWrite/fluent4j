package net.quickwrite.fluent4j.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.impl.ast.pattern.container.FluentArgumentContainer;

import java.io.IOException;

public interface ArgumentList {
    ArgumentList EMPTY = new FluentArgumentContainer();

    NamedArgument getArgument(final String name);
    FluentPattern getArgument(final int index);

    default String getArgumentAsString(int index, final FluentScope scope) throws IOException {
        return getArgument(index).toSimpleString(scope);
    }

    default String getArgumentAsString(final String name, final FluentScope scope) throws IOException {
        return getArgument(name).toSimpleString(scope);
    }

    interface NamedArgument extends FluentPattern {

    }
}
