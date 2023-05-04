package net.quickwrite.fluent4j.impl.iterator;

import net.quickwrite.fluent4j.iterator.ContentIterator;

public final class StringContentIterator implements ContentIterator {
    private final String[] lines;
    private int line;
    private int characterIndex;

    public StringContentIterator(final String input) {
        this.lines = input.lines().toArray(String[]::new);
        this.line = 0;
        this.characterIndex = 0;
    }

    @Override
    public String inputName() {
        return "String";
    }

    @Override
    public int character() {
        if (line() == null || line().length() < this.characterIndex) {
            return 0;
        }

        if (line().length() == this.characterIndex) {
            return '\n';
        }

        return line().codePointAt(characterIndex);
    }

    @Override
    public int nextChar() {
        if (line() == null) {
            return 0;
        }

        if (line().length() < this.characterIndex + 1) {
            this.characterIndex = 0;
            if (nextLine() == null) {
                return 0;
            }

            return character();
        }

        if(line().codePointAt(characterIndex) > Character.MAX_VALUE) {
            this.characterIndex++;
        }
        this.characterIndex++;

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
        line++;
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
