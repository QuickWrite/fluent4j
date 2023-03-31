package net.quickwrite.fluent4j.iterator;

public interface ContentIterator {
    String inputName();

    int character();
    int nextChar();

    String line();
    String nextLine();

    int[] position();
    void setPosition(final int[] position);
}
