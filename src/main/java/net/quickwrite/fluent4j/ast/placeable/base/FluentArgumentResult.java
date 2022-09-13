package net.quickwrite.fluent4j.ast.placeable.base;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.bundle.DirectFluentBundle;

public interface FluentArgumentResult {
    FluentElement getArgumentResult(final DirectFluentBundle bundle, final FluentArgs arguments);
}
