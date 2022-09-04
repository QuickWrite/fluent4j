package net.quickwrite.fluent4j;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.ast.*;
import net.quickwrite.fluent4j.exception.UnknownElementException;
import net.quickwrite.fluent4j.functions.AbstractFunction;
import net.quickwrite.fluent4j.util.BuiltinFunctions;
import net.quickwrite.fluent4j.util.args.FluentArgs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bundles are single-language stores of translations.  They are
 * aggregate parsed Fluent resources in the Fluent syntax and can
 * format translation units (entities) to strings.
 */
public class FluentBundle {
    /**
     * The FluentBundle itself does only operate on only
     * one specific locale (the first) but it can have other locales
     * that act as a fallback when a function is not supported.
     */
    private final ULocale locale;

    private final Map<String, FluentTerm> terms;
    private final Map<String, FluentMessage> messages;

    private final Map<String, AbstractFunction> functions = BuiltinFunctions.getBuiltinFunctions();

    public FluentBundle(ULocale locale, final FluentResource resource) {
        this.locale = locale;
        this.messages = new HashMap<>();
        this.terms = new HashMap<>();

        this.addResource(resource);
    }

    /**
     * Adds a {@link FluentResource} to the dataset
     * of terms and messages so that they can be easily
     * accessed.
     *
     * <p>
     * If a term or a message key exists twice the old
     * key is getting discarded and the new overrides it.
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
        for (FluentElement element : resource.getElements()) {
            if (element instanceof FluentTerm) {
                FluentTerm term = (FluentTerm) element;
                terms.put(term.getIdentifier(), term);

                continue;
            }

            if (element instanceof FluentMessage) {
                FluentMessage message = (FluentMessage) element;
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

    public String getMessage(final String key, final FluentArgs arguments) {
        final FluentMessage message = this.getMessage(key);

        if (message == null) {
            return "{" + key + "}";
        }

        return message.getResult(this, arguments);
    }

    public ULocale getLocale() {
        return this.locale;
    }
}
