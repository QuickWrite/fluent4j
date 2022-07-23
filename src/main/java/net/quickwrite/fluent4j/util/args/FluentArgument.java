package net.quickwrite.fluent4j.util.args;

public interface FluentArgument<T> {
    T valueOf();

    boolean matches(String selector);

    String stringValue();
}
