package net.quickwrite.fluent4j.util.args;

public abstract class FluentArgument<T> {
    private final T value;

    protected FluentArgument(T value) {
        this.value = value;
    }

    public T valueOf() {
        return this.value;
    }

    public abstract boolean matches(String selector);

    public abstract String toString();
}
