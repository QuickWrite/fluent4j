package net.quickwrite.fluent4j.util.bundle;

import net.quickwrite.fluent4j.ast.FluentMessage;
import net.quickwrite.fluent4j.ast.FluentTerm;
import net.quickwrite.fluent4j.util.bundle.args.AccessorBundle;

import java.util.Optional;

public interface DirectFluentBundle extends FluentBundle {
    /**
     * Returns the {@link FluentTerm} that is being stored
     * for the {@code key}.
     *
     * <p>
     * So when the {@code .ftl}-files contain:
     * <pre>
     *     -test = Hello World!
     * </pre>
     * it would return the {@link FluentTerm} for the key
     * {@code test} but {@code null} for anything else.
     *
     * @param key The key that the {@link FluentTerm} is stored in.
     * @return The term itself
     */
    FluentTerm getTerm(final String key);

    /**
     * Returns the {@link FluentTerm} that is being stored
     * for the {@code key}.
     *
     * <p>
     * So when the {@code .ftl}-files contain:
     * <pre>
     *     -test = Hello World!
     * </pre>
     * it would return the {@link FluentTerm} for the key
     * {@code test} but {@code null} for anything else.
     *
     * @param bundle The bundle that is being used as an argument
     * @param key    The key that the {@link FluentTerm} is stored in.
     * @return The term itself
     */
    Optional<String> getTerm(final String key, final AccessorBundle bundle);

    /**
     * Returns the {@link FluentMessage} that is being stored
     * for the {@code key}.
     *
     * <p>
     * So when the {@code .ftl}-files contain:
     * <pre>
     *     test = Hello World!
     * </pre>
     * <p>
     * it would return the {@link FluentMessage} for the key
     * {@code test} but {@code null} for anything else.
     *
     * @param key The key that the {@link FluentMessage} is stored in.
     * @return The message itself
     */
    FluentMessage getMessage(final String key);

    /**
     * Returns the {@link FluentMessage} that is being stored
     * for the {@code key}.
     *
     * <p>
     * So when the {@code .ftl}-files contain:
     * <pre>
     *     test = Hello World!
     * </pre>
     * <p>
     * it would return the {@link FluentMessage} for the key
     * {@code test} but {@code null} for anything else.
     *
     * @param key    The key that the {@link FluentMessage} is stored in.
     * @param bundle The bundle that is being used as an argument
     * @return The message itself
     */
    Optional<String> getMessage(final String key, final AccessorBundle bundle);
}
