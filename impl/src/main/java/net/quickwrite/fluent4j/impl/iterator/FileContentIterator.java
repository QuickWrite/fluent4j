package net.quickwrite.fluent4j.impl.iterator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileContentIterator extends LineContentIterator {
    private final Path path;

    public FileContentIterator(final Path path) throws IOException {
        super(Files.readAllLines(path).toArray(String[]::new));

        this.path = path;
    }

    public FileContentIterator(final File file) throws IOException {
        this(file.toPath());
    }

    @Override
    public String inputName() {
        return path.toString();
    }
}
