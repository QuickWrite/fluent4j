package net.quickwrite.fluent4j.test.util;

import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.iterator.FluentIteratorFactory;
import net.quickwrite.fluent4j.parser.ResourceParser;
import net.quickwrite.fluent4j.parser.ResourceParserBuilder;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

public class FluentUtils {
    public static <B extends ResultBuilder> FluentResource<B> getResourceFromString(final String string) {
        final ResourceParser<B> resourceParser = ResourceParserBuilder.defaultParser();

        return resourceParser.parse(FluentIteratorFactory.fromString(string));
    }

    public static <B extends ResultBuilder> FluentResource<B> getResourceFromResource(final String string) {
        try {
            final ResourceParser<B> resourceParser = ResourceParserBuilder.defaultParser();

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
