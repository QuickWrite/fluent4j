package net.quickwrite.fluent4j.util.bundle.args;

import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.bundle.DirectFluentBundle;

public class AccessorElementsBundle implements AccessorBundle {
    private final DirectFluentBundle bundle;
    private final FluentArgs arguments;

    public AccessorElementsBundle(final DirectFluentBundle bundle, final FluentArgs arguments) {
        this.bundle = bundle;
        this.arguments = arguments;
    }

    @Override
    public DirectFluentBundle getBundle() {
        return this.bundle;
    }

    @Override
    public FluentArgs getArguments() {
        return this.arguments;
    }
}
