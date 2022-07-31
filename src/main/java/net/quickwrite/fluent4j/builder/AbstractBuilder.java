package net.quickwrite.fluent4j.builder;

public abstract class AbstractBuilder<T> {
    protected final T element;

    public AbstractBuilder(T element) {
        this.element = element;
    }

    public T build() {
        return this.element;
    }
}
