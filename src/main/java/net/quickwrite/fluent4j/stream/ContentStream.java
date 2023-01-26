package net.quickwrite.fluent4j.stream;

import java.util.Iterator;

public interface ContentStream extends Iterator<Character> {
    long getPosition();
    void setPosition(final long position);

    String getLine();
}
