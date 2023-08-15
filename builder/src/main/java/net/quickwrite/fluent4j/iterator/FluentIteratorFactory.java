package net.quickwrite.fluent4j.iterator;

import net.quickwrite.fluent4j.impl.iterator.FileContentIterator;
import net.quickwrite.fluent4j.impl.iterator.StringContentIterator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public final class FluentIteratorFactory {
    public static ContentIterator fromString(final String input) {
        return new StringContentIterator(input);
    }

    public static ContentIterator fromFile(final File file) throws IOException {
        return new FileContentIterator(file);
    }

    public static ContentIterator fromPath(final Path path) throws IOException {
        return new FileContentIterator(path);
    }
}
