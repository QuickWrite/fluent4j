package net.quickwrite.fluent4j.util.bundle;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.ast.FluentMessage;
import net.quickwrite.fluent4j.ast.FluentTerm;
import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.exception.RecursionDepthReachedException;
import net.quickwrite.fluent4j.exception.UnknownElementException;
import net.quickwrite.fluent4j.functions.AbstractFunction;
import net.quickwrite.fluent4j.util.BuiltinFunctions;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.bundle.args.AccessorBundle;
import net.quickwrite.fluent4j.util.bundle.args.AccessorElementsBundle;

import java.util.*;

/**
 * Bundles are single-language stores of translations. They are
 * aggregate parsed Fluent resources in the Fluent syntax and can
 * format translation units (entities) to strings.
 */
public class ResourceFluentBundle implements FluentBundle, DirectFluentBundle {
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
     *
     * @param locale   The locale that the bundle should act on
     * @param resource The resource that the bundle should start with
     */
    public ResourceFluentBundle(final ULocale locale, final FluentResource resource) {
        this.locale = locale;
        this.messages = new HashMap<>();
        this.terms = new HashMap<>();
        this.exceptionList = new ArrayList<>();

        this.addResource(resource);
    }

    @Override
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

    @Override
    public void addFunction(final AbstractFunction function) {
        this.functions.put(function.getIdentifier(), function);
    }

    @Override
    public Optional<AbstractFunction> getFunction(final String key) {
        return Optional.ofNullable(this.functions.get(key));
    }

    @Override
    public boolean hasMessage(final String key) {
        return this.messages.containsKey(key);
    }

    @Override
    public Optional<String> getMessage(final String key, final FluentArgs arguments, int recursionDepth) {
        return getMessage(
                key,
                new AccessorElementsBundle(
                        this,
                        arguments != null ? arguments : FluentArgs.EMPTY_ARGS
                ),
                recursionDepth
        );
    }

    @Override
    public FluentTerm getTerm(final String key) {
        return this.terms.get(key);
    }

    @Override
    public Optional<String> getTerm(final String key, final AccessorBundle bundle, int recursionDepth) {
        final FluentTerm term = this.getTerm(key);

        if (term == null) {
            return Optional.empty();
        }

        if (recursionDepth <= 0) {
            throw new RecursionDepthReachedException();
        }

        return Optional.of(
                term.getResult(bundle, recursionDepth).toString()
        );
    }

    @Override
    public FluentMessage getMessage(final String key) {
        return this.messages.get(key);
    }

    @Override
    public Optional<String> getMessage(final String key, final AccessorBundle bundle, int recursionDepth) {
        final FluentMessage message = this.getMessage(key);

        if (message == null) {
            return Optional.empty();
        }

        if (recursionDepth <= 0) {
            throw new RecursionDepthReachedException();
        }

        return Optional.of(
                message.getResult(bundle, recursionDepth).toString()
        );
    }

    @Override
    public ULocale getLocale() {
        return this.locale;
    }

    @Override
    public boolean hasExceptions() {
        return !this.exceptionList.isEmpty();
    }

    @Override
    public List<FluentParseException> getExceptionList() {
        return this.exceptionList;
    }

    @Override
    public String toString() {
        return "ResourceFluentBundle: {\n" +
                "\tlocale: " + this.locale + "\n" +
                "\tterms: [\n\t\t" + this.terms + "\n\t]\n" +
                "\tmessages: [\n\t\t" + this.messages + "\n\t]\n" +
                "\tfunctions: [\n\t\t" + this.functions + "\n\t]\n" +
                "\texceptions: [\n\t\t" + this.exceptionList + "\n\t]\n" +
                "}";
    }
}
