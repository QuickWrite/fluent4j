package net.quickwrite.fluent4j.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.impl.ast.pattern.container.FluentArgumentContainer;

import java.io.IOException;

public interface ArgumentList {
    ArgumentList EMPTY = new FluentArgumentContainer();

    NamedArgument getArgument(final String name);
    FluentPattern getArgument(final int index);

    interface NamedArgument extends FluentPattern {

    }
}
