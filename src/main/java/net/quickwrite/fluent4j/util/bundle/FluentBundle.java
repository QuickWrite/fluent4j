package net.quickwrite.fluent4j.util.bundle;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.FluentResource;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.functions.AbstractFunction;
import net.quickwrite.fluent4j.util.args.FluentArgs;

import java.util.List;

public interface FluentBundle {
    /**
     * Adds a {@link FluentResource} to the dataset
     * of terms and messages so that they can be easily
     * accessed.
     *
     * <p>
     * If a term or a message key exists multiple times
     * the old key is getting discarded and the new
     * overrides it.
     * <p>
     * This manual addition of different resources can be
     * used for different files so that they don't need
     * to be concatenated before they are getting parsed.
     *
     * @param resource The resource that is being added.
     */
    void addResource(final FluentResource resource);

    /**
     * Adds a function to the list of functions that can be accessed
     * by the entire FluentBundle.
     *
     * @param function The function itself that should be called.
     */
    void addFunction(final AbstractFunction function);

    /**
     * Returns a function that has the name {@code key}.
     *
     * <p>
     * FluentFunctions are <strong>always</strong> being
     * written in uppercase. So a key like {@code tEST} will
     * <strong>always</strong> return {@code false}.
     *
     * @param key the key that the function is stored in
     * @return The function
     */
    AbstractFunction getFunction(final String key);

    /**
     * Checks if a message with the {@code key}
     * exists.
     *
     * @param key The key the message should be stored in
     * @return If the message is available
     */
    boolean hasMessage(final String key);

    /**
     * Returns the result of a Message with a specific
     * key. Parameters are optional and can be (if not needed)
     * called with a {@code null} value.
     *
     * <p>
     * When the key is {@code hello-world} and there was
     * a {@link FluentBundle} added that had the Message
     * <pre>
     *     hello-world = Hi! How are you?
     * </pre>
     * it will return the String {@code Hi! How are you?}.
     * <p>
     * In the case of needed parameters (if variables are
     * in the Message) then the {@link FluentArgs} with the variable
     * should be defined.
     * <br>
     * So then the call of {@code getMessage("hello-world-with-test", arguments)}
     * on a FluentBundle with the Message:
     * <pre>
     *     hello-world-with-test = Hi! How are you { $name }?
     * </pre>
     * with the arguments having the variable {@code $name = "Max"} it would
     * return {@code Hi! How are you Max?}.
     *
     * <p>
     * If the message does not exist it returns a string with the format of
     * <code>{ + key + }</code> which means that when the key is {@code test}
     * the return value would be <code>{test}</code>.
     *
     * @param key       The key of the message
     * @param arguments The arguments that should be passed on
     * @return The generated string
     */
    String getMessage(final String key, final FluentArgs arguments);

    /**
     * Returns the locale the bundle has.
     *
     * @return The locale
     */
    ULocale getLocale();

    /**
     * Checks if the bundle itself contains
     * exceptions that have been thrown at
     * the processing of the {@code .ftl}-files.
     *
     * @return If there are any exceptions
     */
    boolean hasExceptions();

    /**
     * Returns all exceptions that have been thrown
     * at the processing of the {@code .ftl}-files.
     *
     * <p>
     * The exceptions are all combined when the
     * {@link FluentResource}s are getting added.
     *
     * @return All of the exceptions as a list.
     */
    List<FluentParseException> getExceptionList();
}
