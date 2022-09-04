package net.quickwrite.fluent4j.builder;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.functions.AbstractFunction;
import net.quickwrite.fluent4j.parser.FluentParser;

import java.io.File;

/**
 * The builder class for the {@link FluentBundle} class.
 */
public class FluentBundleBuilder extends AbstractBuilder<FluentBundle> {
    /**
     * <p>
     * Creates a new FluentBundleBuilder with the necessary parameters
     * for a {@link FluentBundle}.
     * </p>
     * <p>
     * A bundle itself is a collection of different messages that can
     * be accessed and built with the necessary parameters.
     * </p>
     * <p>
     * If there are duplicate messages like
     * <pre>
     *     # This is the first message
     *     test = Hello World
     *
     *     # This is the second message
     *     test = This is the text that overwrites
     * </pre>
     * then the last message will override the previous message so that
     * when {@code test} is getting called the result will be
     * {@code This is the text that overwrites} and not {@code Hello World}.
     * </p>
     * <p>
     * All of the error messages that are getting thrown at the parsing
     * step are getting saved in the FluentBundle and can be accessed
     * by using the methods {@link FluentBundle#hasExceptions()} and
     * {@link FluentBundle#getExceptionList()}.
     * </p>
     *
     * @param locale The language that the bundle should have
     * @param file   The file itself that should be parsed
     */
    public FluentBundleBuilder(final ULocale locale, final File file) {
        this(locale, file.toString());
    }

    /**
     * <p>
     * Creates a new FluentBundleBuilder with the necessary parameters
     * for a {@link FluentBundle}.
     * </p>
     * <p>
     * A bundle itself is a collection of different messages that can
     * be accessed and built with the necessary parameters.
     * </p>
     * <p>
     * If there are duplicate messages like
     * <pre>
     *     # This is the first message
     *     test = Hello World
     *
     *     # This is the second message
     *     test = This is the text that overwrites
     * </pre>
     * then the last message will override the previous message so that
     * when {@code test} is getting called the result will be
     * {@code This is the text that overwrites} and not {@code Hello World}.
     * </p>
     * <p>
     * All of the error messages that are getting thrown at the parsing
     * step are getting saved in the FluentBundle and can be accessed
     * by using the methods {@link FluentBundle#hasExceptions()} and
     * {@link FluentBundle#getExceptionList()}.
     * </p>
     *
     * @param locale      The language that the bundle should have
     * @param fileContent The file itself that should be parsed
     */
    public FluentBundleBuilder(final ULocale locale, final String fileContent) {
        super(new FluentBundle(locale, FluentParser.parse(fileContent)));
    }

    /**
     * <p>
     * Adds another resource to the bundle.
     * </p>
     * <p>
     * All of the messages that overlap are going to be
     * overwritten. This means that when there is a case like
     * <pre>
     *     # This is the first message
     *     test = Hello World
     *
     *     # This is the second message
     *     test = This is the text that overwrites
     * </pre>
     * it will return {@code This is the text that overwrites}
     * when {@code test} is getting called.
     * </p>
     *
     * @param file The file itself that should be parsed
     * @return The FluentBundleBuilder object itself
     */
    public FluentBundleBuilder addResource(final File file) {
        return addResource(file.toString());
    }

    /**
     * <p>
     * Adds another resource to the bundle.
     * </p>
     * <p>
     * All of the messages that overlap are going to be
     * overwritten. This means that when there is a case like
     * <pre>
     *     # This is the first message
     *     test = Hello World
     *
     *     # This is the second message
     *     test = This is the text that overwrites
     * </pre>
     * it will return {@code This is the text that overwrites}
     * when {@code test} is getting called.
     * </p>
     *
     * @param fileContent The file itself that should be parsed.
     * @return The FluentBundleBuilder object itself
     */
    public FluentBundleBuilder addResource(final String fileContent) {
        this.element.addResource(FluentParser.parse(fileContent));

        return this;
    }

    /**
     * <p>
     * Adds an {@link AbstractFunction} to the {@link FluentBundle}
     * that can be accessed by every message in the FluentBundle.
     * </p>
     * <p>
     * All of the functions can be called by having {@code FUNCTIONNAME()}
     * in a placeable. <br>
     * In these calls there can be named and positional parameters.
     * </p>
     *
     * @param function The function itself that should be added to the context
     * @return The FluentBundleBuilder object itself
     */
    public FluentBundleBuilder addFunction(final AbstractFunction function) {
        this.element.addFunction(function);

        return this;
    }
}
