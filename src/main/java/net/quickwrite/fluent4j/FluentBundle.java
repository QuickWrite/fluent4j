package net.quickwrite.fluent4j;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.ast.FluentMessage;
import net.quickwrite.fluent4j.ast.FluentTerm;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.exception.UnknownElementException;
import net.quickwrite.fluent4j.functions.AbstractFunction;
import net.quickwrite.fluent4j.util.BuiltinFunctions;
import net.quickwrite.fluent4j.util.args.FluentArgs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bundles are single-language stores of translations.  They are
 * aggregate parsed Fluent resources in the Fluent syntax and can
 * format translation units (entities) to strings.
 */
public class FluentBundle {
    private final ULocale locale;

    private final Map<String, FluentTerm> terms;
    private final Map<String, FluentMessage> messages;

    private final List<FluentParseException> exceptionList;

    private final Map<String, AbstractFunction> functions = BuiltinFunctions.getBuiltinFunctions();

    /**
     * Creates a new FluentBundle that encapsulates all of
     * the Messages that are getting called.
     *
     * <p>
     * The bundle itself does need at least one {@link FluentResource}
     * to be instantiated.
     * </p>
     *
     * @param locale   The locale that the bundle should act on
     * @param resource The ressource that the bundle should start with
     */
    public FluentBundle(ULocale locale, final FluentResource resource) {
        this.locale = locale;
        this.messages = new HashMap<>();
        this.terms = new HashMap<>();
        this.exceptionList = new ArrayList<>();

        this.addResource(resource);
    }

    /**
     * Adds a {@link FluentResource} to the dataset
     * of terms and messages so that they can be easily
     * accessed.
     *
     * <p>
     * If a term or a message key exists multiple times
     * the old key is getting discarded and the new
     * overrides it.
     * </p>
     * <p>
     * This manual addition of different resources can be
     * used for different files so that they don't need
     * to be concatenated before they are getting parsed.
     * </p>
     *
     * @param resource The resource that is being added.
     */
    public void addResource(final FluentResource resource) {
        for (final FluentElement element : resource.getElements()) {
            if (element instanceof FluentTerm) {
                final FluentTerm term = (FluentTerm) element;
                terms.put(term.getIdentifier(), term);

                continue;
            }

            if (element instanceof FluentMessage) {
                final FluentMessage message = (FluentMessage) element;
                messages.put(message.getIdentifier(), message);

                continue;
            }

            throw new UnknownElementException(
                    "Expected an object of type FluentTerm or FluentMessage but got an element of type: " +
                            element.getClass().getTypeName() +
                            "\n" +
                            "Object: " +
                            element
            );
        }

        if (resource.hasExceptions()) {
            this.exceptionList.addAll(resource.getExceptions());
        }
    }

    /**
     * <p>
     * Adds a function to the list of functions that can be accessed
     * by the entire FluentBundle.
     * </p>
     *
     * @param function The function itself that should be called.
     */
    public void addFunction(final AbstractFunction function) {
        this.functions.put(function.getIdentifier(), function);
    }

    public AbstractFunction getFunction(final String key) {
        return this.functions.get(key);
    }

    public boolean hasMessage(final String key) {
        return this.messages.containsKey(key);
    }

    public FluentTerm getTerm(final String key) {
        return this.terms.get(key);
    }

    public FluentMessage getMessage(final String key) {
        return this.messages.get(key);
    }

    /**
     * <p>
     * Returns the result of a Message with a specific
     * key. Parameters are optional and can be (if not needed)
     * called with a {@code null} value.
     * </p>
     *
     * <p>
     * When the key is {@code hello-world} and there was
     * a {@link FluentBundle} added that had the Message
     * <pre>
     *     hello-world = Hi! How are you?
     * </pre>
     * it will return the String {@code Hi! How are you?}.
     * </p>
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
     * </p>
     *
     * <p>
     * If the message does not exist it returns a string with the format of
     * <code>{ + key + }</code> which means that when the key is {@code test}
     * the return value would be <code>{test}</code>.
     * </p>
     *
     * @param key       The key of the message
     * @param arguments The arguments that should be passed on
     * @return The generated string
     */
    public CharSequence getMessage(final String key, final FluentArgs arguments) {
        final FluentMessage message = this.getMessage(key);

        if (message == null) {
            return "{" + key + "}";
        }

        return message.getResult(this, arguments != null ? arguments : FluentArgs.EMPTY_ARGS);
    }

    public ULocale getLocale() {
        return this.locale;
    }

    public boolean hasExceptions() {
        return !this.exceptionList.isEmpty();
    }

    public List<FluentParseException> getExceptionList() {
        return this.exceptionList;
    }
}
