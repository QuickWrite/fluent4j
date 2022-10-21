package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.ast.placeable.NumberLiteral;
import net.quickwrite.fluent4j.ast.placeable.SelectExpression;
import net.quickwrite.fluent4j.util.bundle.DirectFluentBundle;
import net.quickwrite.fluent4j.util.bundle.args.AccessorBundle;

/**
 * The base interface for the AST.
 */
public interface FluentElement {
    /**
     * Checks if the selector matches the argument.
     *
     * <p>
     * This is only being used in {@link SelectExpression}s for the
     * single selectables that are being checked against.
     *
     * <p>
     * This matches method does not always equal the {@code equals()}
     * method as in some cases (like with the {@link NumberLiteral}) it
     * does some extra checks so that things like plural rules are
     * getting considered.
     *
     * @param bundle   The base bundle
     * @param selector The other element
     * @return If both are matching
     */
    boolean matches(final DirectFluentBundle bundle, final FluentElement selector);

    /**
     * Returns the String value of the object
     * that the Argument is holding.
     *
     * @return A string
     */
    String stringValue();

    /**
     * Returns the value that the argument has with
     * the different arguments and the base bundle that
     * it has been called from.
     *
     * @param bundle The bundle that is getting passed down
     * @param recursionDepth The amount of recursive calls that can still be made
     * @return The {@link CharSequence} value of the argument with the parameters
     */
    CharSequence getResult(final AccessorBundle bundle, final int recursionDepth);
}
