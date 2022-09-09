package net.quickwrite.fluent4j.ast.placeable.base;

import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;
import net.quickwrite.fluent4j.util.bundle.ResourceFluentBundle;

public interface FluentArgumentResult {
    FluentArgument getArgumentResult(final ResourceFluentBundle bundle, final FluentArgs arguments);
}
