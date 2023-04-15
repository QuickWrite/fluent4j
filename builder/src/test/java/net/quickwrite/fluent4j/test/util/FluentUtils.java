package net.quickwrite.fluent4j.test.util;

import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.iterator.StringIteratorFactory;
import net.quickwrite.fluent4j.parser.ResourceParser;
import net.quickwrite.fluent4j.parser.ResourceParserBuilder;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class FluentUtils {
    public static <B extends ResultBuilder> FluentResource<B> getResourceFromString(final String string) {
        final ResourceParser<B> resourceParser = ResourceParserBuilder.defaultParser();

        return resourceParser.parse(StringIteratorFactory.from(string));
    }

    public static <B extends ResultBuilder> FluentResource<B> getResourceFromResource(final String string) {
        try {
            return getResourceFromString(getResourceFileAsString(string));
        } catch (final IOException exception) {
            throw new RuntimeException();
        }
    }

    /**
     * Reads given resource file as a string.
     *
     * @param fileName path to the resource file
     * @return the file's contents
     * @throws IOException if read fails for any reason
     */
    private static String getResourceFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }
}
