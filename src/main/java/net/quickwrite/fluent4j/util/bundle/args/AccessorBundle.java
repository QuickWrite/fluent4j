package net.quickwrite.fluent4j.util.bundle.args;

import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.bundle.DirectFluentBundle;

public interface AccessorBundle {
    DirectFluentBundle getBundle();

    FluentArgs getArguments();
}
