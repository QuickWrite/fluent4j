package net.quickwrite.fluent4j.impl.iterator;

public final class StringContentIterator extends LineContentIterator {
    public StringContentIterator(final String content) {
        super(content.lines().toArray(String[]::new));
    }

    @Override
    public String inputName() {
        return "String";
    }
}
