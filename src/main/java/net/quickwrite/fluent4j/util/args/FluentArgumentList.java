package net.quickwrite.fluent4j.util.args;

import net.quickwrite.fluent4j.ast.wrapper.FluentArgument;

public interface FluentArgumentList {
    FluentArgument get(String key);

    void set(String key, FluentArgument argument);
}
