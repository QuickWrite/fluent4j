package net.quickwrite.fluent4j.test.util;

import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.iterator.FluentIteratorFactory;
import net.quickwrite.fluent4j.parser.ResourceParser;
import net.quickwrite.fluent4j.parser.ResourceParserBuilder;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class FluentUtils {
    public static FluentResource getResourceFromString(final String string) {
        final ResourceParser resourceParser = ResourceParserBuilder.defaultParser();

        return resourceParser.parse(FluentIteratorFactory.fromString(string));
    }

    public static FluentResource getResourceFromResource(final String string) {
        try {
            final ResourceParser resourceParser = ResourceParserBuilder.defaultParser();

            return resourceParser.parse(FluentIteratorFactory.fromFile(getResourceFileAsFile(string)));
        } catch (final IOException | URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Reads given resource file as a string.
     *
     * @param fileName path to the resource file
     * @return the file's contents
     * @throws URISyntaxException If the action fails for some reason
     */
    private static File getResourceFileAsFile(String fileName) throws URISyntaxException {
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        final URL url = classLoader.getResource(fileName);
        assert url != null;
        return new File(url.toURI());
    }
}
