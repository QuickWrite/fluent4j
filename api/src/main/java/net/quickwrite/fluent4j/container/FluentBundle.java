package net.quickwrite.fluent4j.container;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentFunction;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * A collection of localization messages for a single locale, which are meant
 * to be used together in a single view, widget or any other UI abstraction. <br />
 * This also contains the different functions and other types of messages like
 * terms as they are dependent on the locale.
 *
 * @param <B> The type of ResultBuilder associated with the resolvable entities.
 */
public interface FluentBundle<B extends ResultBuilder> {
    /**
     * Returns if the message with this specific
     * key does exist.
     *
     * @param key The key to check against
     * @return True if the message exist
     */
    boolean hasMessage(final String key);

    /**
     * Returns a set of all messages with their
     * respective key.
     *
     * @return All messages
     */
    Set<Map.Entry<String, FluentEntry<B>>> getMessages();

    /**
     * Returns the message with the given key.
     * <br />
     * If the message doesn't exist it will return
     * an empty optional.
     *
     * @param key The key of the message
     * @return The message or an empty optional
     */
    Optional<FluentEntry<B>> getMessage(final String key);

    /**
     * Returns the Builder itself that was being given
     * which was being run over the entire tree of the
     * message with the given key.
     * <br />
     * If the message doesn't exist it will return
     * an empty optional.
     *
     * <p>
     *     With that the message will be resolved and
     *     converted into a format that is being
     *     concatenated and can easily be used.
     *     <br />
     *     This means that the message will now use
     *     terms, select expressions, variables etc.
     *     to return a readable message that can
     *     be used as a result.
     * </p>
     *
     * @param key The key of the message
     * @param argumentList A list of arguments
     * @param builder The builder that should be used
     * @return The builder or an empty optional
     */
    Optional<B> resolveMessage(final String key, final ArgumentList<B> argumentList, final B builder);
    /**
     * Returns the Builder itself that was being given
     * which was being run over the entire tree of the
     * message with the given key.
     * <br />
     * If the message doesn't exist it will return
     * an empty optional.
     *
     * <p>
     *     With that the message will be resolved and
     *     converted into a format that is being
     *     concatenated and can easily be used.
     *     <br />
     *     This means that the message will now use
     *     terms, select expressions, variables etc.
     *     to return a readable message that can
     *     be used as a result.
     * </p>
     * <p>
     *     This will use an empty argument list
     *     as the arguments.
     * </p>
     *
     * @param key The key of the message
     * @param builder The builder that should be used
     * @return The builder or an empty optional
     */
    Optional<B> resolveMessage(final String key, final B builder);

    /**
     * Returns all the entries of the specified class
     * type as a set with their respective key.
     *
     * @param clazz The class that the entries should have
     * @return All elements
     * @param <T> The generic type that class can have
     */
    <T extends FluentEntry<B>> Set<Map.Entry<String, FluentEntry<B>>> getEntries(final Class<T> clazz);

    /**
     * Returns a single entry with the specific key
     * specified with that specific class.
     * <br />
     * If the entry doesn't exist it will return
     * an empty optional.
     *
     * @param key The key of the entry
     * @param clazz The class that the entry should have
     * @return The entry or an empty optional
     * @param <T> The generic type that class can have
     */
    <T extends FluentEntry<B>> Optional<T> getEntry(final String key, final Class<T> clazz);

    /**
     * Returns the locale that this specific bundle
     * has.
     * <br />
     * The bundle itself is being based around a single
     * locale that all the messages, terms, functions etc.
     * have.
     *
     * @return The locale of the bundle
     */
    ULocale getLocale();

    /**
     * Returns the function with the specific key.
     * <br />
     * If the function doesn't exist it will return
     * an empty optional.
     *
     * <p>
     *     Function names normally only have
     *     upper-case letters which means
     *     that a function with the name of
     *     {@code Test} is probably not valid and
     *     with that not in this list.
     * </p>
     *
     * @param key The key (aka. the name) of the function
     * @return The function itself or an empty optional
     */
    Optional<FluentFunction<B>> getFunction(final String key);

    /**
     * Returns all the functions as a set.
     *
     * <p>
     *     As the functions store their name
     *     themselves the set does not contain their
     *     names.
     * </p>
     *
     * @return All the functions
     */
    Set<FluentFunction<B>> getFunctions();

    interface Builder<B extends ResultBuilder> extends net.quickwrite.fluent4j.util.Builder<FluentBundle<B>> {
        Builder<B> addResource(final FluentResource<B> resource);

        Builder<B> addResourceNoDup(final FluentResource<B> resource);

        Builder<B> addFunction(final FluentFunction<B> function);

        Builder<B> addDefaultFunctions();
    }
}
