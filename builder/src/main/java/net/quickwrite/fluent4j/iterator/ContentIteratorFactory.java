package net.quickwrite.fluent4j.iterator;

public interface ContentIteratorFactory<T> {
    ContentIterator create(final T input);
}
