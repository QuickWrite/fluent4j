package net.quickwrite.fluent4j;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.ast.FluentMessage;
import net.quickwrite.fluent4j.ast.FluentTerm;
import net.quickwrite.fluent4j.functions.AbstractFunction;

import java.util.*;

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

    private Map<String, AbstractFunction> functions;

    public FluentBundle(final List<Locale> locales, final FluentResource resource) {
        this.locales = locales;
        this.messages = new HashMap<>();
        this.terms = new HashMap<>();

        addResource(resource);
    }

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
                FluentMessage message = (FluentMessage)element;
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
     * @param function The function itself that should be called.
     */
    public void addFunction(final AbstractFunction function) {
        functions.put(function.getIdentifier(), function);
    }

    public Locale getLocale() {
        return locales.get(0);
    }
}
