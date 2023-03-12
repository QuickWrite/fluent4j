package net.quickwrite.fluent4j.iterator;

import net.quickwrite.fluent4j.impl.iterator.StringContentIterator;

public interface ContentIterator {
    String inputName();

    int character();
    int nextChar();

    String line();
    String nextLine();

    int[] position();
    void setPosition(final int[] position);

    static ContentIterator from(final String input) {
        return new StringContentIterator(input);
    }
}
