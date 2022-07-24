package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.ast.*;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.functions.AbstractFunction;
import net.quickwrite.fluent4j.util.BuiltinFunctions;
import net.quickwrite.fluent4j.util.args.FluentArgs;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    private final List<Locale> locales;

    private Map<String, FluentTerm> terms;
    private Map<String, FluentMessage> messages;

    private Map<String, AbstractFunction> functions = BuiltinFunctions.getBuiltinFunctions();

    public FluentBundle(final List<Locale> locales, final FluentResource resource) {
        this.locales = locales;
        this.messages = new HashMap<>();
        this.terms = new HashMap<>();

        this.addResource(resource);
    }

    public FluentBundle(final Locale locale, final FluentResource resource) {
        this(List.of(locale), resource);
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
                if (terms.put(term.getIdentifier().toString(), term) != null) {
                    // TODO: handle duplicate terms
                }

                continue;
            }

            if (element instanceof FluentMessage) {
                FluentMessage message = (FluentMessage) element;
                if (messages.put(message.getIdentifier().toString(), message) != null) {
                    // TODO: handle duplicate messages
                }

                continue;
            }

            // TODO: handle unknown elements
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

    public boolean hasMessage(final String key) {
        return this.messages.containsKey(key);
    }

    public String getTerm(final String key, final FluentArgs arguments) {
        return this.getBase(this.terms.get(key), arguments);
    }

    public String getMessage(final String key, final FluentArgs arguments) {
        return this.getBase(this.messages.get(key), arguments);
    }

    private String getBase(final FluentBase base, final FluentArgs arguments) {
        StringBuilder builder = new StringBuilder();

        for (FluentElement element : base.getElements()) {
            if (element instanceof FluentTextElement) {
                builder.append(((FluentTextElement) element).getText());
            } else {
                builder.append(((FluentPlaceable) element).getResult(this, arguments));
            }
        }

        return builder.toString();
    }

    public Locale getLocale() {
        return this.locales.get(0);
    }
}
