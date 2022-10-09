package net.quickwrite.fluent4j.ast.placeable.base;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.util.bundle.args.AccessorBundle;

public interface FluentArgumentResult {
    FluentElement getArgumentResult(AccessorBundle bundle);
}
