package net.quickwrite.fluent4j.iterator;

import net.quickwrite.fluent4j.impl.iterator.StringContentIterator;

public final class StringIteratorFactory implements ContentIteratorFactory<String> {
    @Override
    public ContentIterator create(final String input) {
        return new StringContentIterator(input);
    }
}
