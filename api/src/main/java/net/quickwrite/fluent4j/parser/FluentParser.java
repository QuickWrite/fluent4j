package net.quickwrite.fluent4j.parser;

import net.quickwrite.fluent4j.iterator.ContentIterator;

/**
 * The basic parser interface
 *
 * @param <T> The element that should be returned
 *           by the parser
 */
public interface FluentParser<T> {

    T parse(final ContentIterator iterator);
}
