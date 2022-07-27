package net.quickwrite.fluent4j.util.args;

import net.quickwrite.fluent4j.FluentBundle;

public interface FluentArgument<T> {
    T valueOf();

    boolean matches(final FluentBundle bundle, final FluentArgument<?> selector);

    String stringValue();

    String getResult(final FluentBundle bundle, final FluentArgs arguments);
}
