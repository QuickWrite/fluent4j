package net.quickwrite.fluent4j.ast.placeable.base;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;

public interface FluentArgumentResult {
    FluentArgument getArgumentResult(final FluentBundle bundle, final FluentArgs arguments);
}
