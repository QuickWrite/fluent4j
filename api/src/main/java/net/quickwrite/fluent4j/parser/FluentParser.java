package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.iterator.ContentIterator;

public interface FluentParser<T> {

    T parse(final ContentIterator iterator);
}
