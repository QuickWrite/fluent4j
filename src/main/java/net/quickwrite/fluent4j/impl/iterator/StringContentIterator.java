package net.quickwrite.fluent4j.impl.iterator;

import net.quickwrite.fluent4j.iterator.ContentIterator;

public class StringContentIterator implements ContentIterator {
    private final String[] lines;
    private int line;
    private int characterIndex;

    public StringContentIterator(final String input) {
        this.lines = input.lines().toArray(String[]::new);
        this.line = 0;
        this.characterIndex = 0;
    }

    @Override
    public int character() {
        if (line().length() <= this.characterIndex) {
            return 0;
        }

        return line().codePointAt(characterIndex);
    }

    @Override
    public int nextChar() {
        if (line().length() <= this.characterIndex + 1) {
            this.characterIndex = 0;
            if(nextLine() == null) {
                return 0;
            }
        } else {
            this.characterIndex += 1;
        }
        return character();
    }

    @Override
    public String line() {
        if (lines.length <= line) {
            return null;
        }

        return lines[line];
    }

    @Override
    public String nextLine() {
        line += 1;
        characterIndex = 0;
        return line();
    }

    @Override
    public int[] position() {
        return new int[]{line, characterIndex};
    }

    @Override
    public void setPosition(final int[] position) {
        this.line = position[0];
        this.characterIndex = position[1];
    }
}
