package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.exception.FluentPatternException;
import net.quickwrite.fluent4j.result.ResultBuilder;

/**
 * A pattern itself is the base element for the
 * message. With that a pattern can be a TextElement
 * or a {@link net.quickwrite.fluent4j.ast.placeable.FluentPlaceable}.
 * @param <B>
 */
public interface FluentPattern<B extends ResultBuilder> extends FluentResolvable<B> {
    /**
     * Returns the element that this element links to. If the element
     * itself for example is a Message Reference the message itself should be returned.
     *
     * <hr />
     *
     * If the element contains the necessary information directly
     * (like a simple Text Element) this should return itself.
     *
     * @param scope The scope that the link should be unwrapped in
     * @return The linked element
     *
     * @throws FluentPatternException If the linked element does not exist this Exception should be thrown
     */
    FluentPattern<B> unwrap(final FluentScope<B> scope) throws FluentPatternException;

    /**
     * Returns a simple string variant of the data that this element contains
     *
     * @param scope The scope in which this operation should be done
     * @return A simple string
     */
    String toSimpleString(final FluentScope<B> scope);
}
