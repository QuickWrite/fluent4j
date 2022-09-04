package net.quickwrite.fluent4j.builder;

/**
 * Abstract Builder class for every single builder
 * as a single guiding point.
 *
 * @param <T> The object class that is going to be built
 */
public abstract class AbstractBuilder<T> {
    protected final T element;

    public AbstractBuilder(T element) {
        this.element = element;
    }

    public T build() {
        return this.element;
    }
}
