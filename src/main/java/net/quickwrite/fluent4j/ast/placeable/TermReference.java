package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.ast.FluentTerm;
import net.quickwrite.fluent4j.ast.placeable.base.FluentFunction;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.bundle.args.AccessorBundle;
import net.quickwrite.fluent4j.util.bundle.args.AccessorElementsBundle;

/**
 * Terms are similar to regular messages but they can
 * only be used as references in other messages. Their
 * identifiers start with a single dash {@code -}
 * like in the example: {@code -brand-name}. The
 * runtime cannot retrieve terms directly. They are best
 * used to define vocabulary and glossary items which
 * can be used consistently across the localization
 * of the entire product.
 *
 * <pre>
 *     -brand-name = Firefox
 *
 *     about = About { -brand-name }.
 *     update-successful = { -brand-name } has been updated.
 * </pre>
 */
public class TermReference extends FluentFunction {
    public TermReference(final StringSlice name) {
        super(name, null);
    }

    public TermReference(final String name, final FluentArgs arguments) {
        super(name, arguments);
    }

    @Override
    protected boolean check(final String string) {
        return true;
    }

    @Override
    public CharSequence getResult(final AccessorBundle bundle) {
        return this.getArgumentResult(bundle).getResult(new AccessorElementsBundle(bundle.getBundle(), this.getArguments(bundle)));
    }

    /**
     * @param bundle    The bundle that this is being called from
     * @return The result of the term that is being called
     */
    @Override
    public FluentElement getArgumentResult(final AccessorBundle bundle) {
        final FluentTerm term = bundle.getBundle().getTerm(this.functionName);

        if (term == null) {
            return new StringLiteral("{-" + this.functionName + "}");
        }

        return term;
    }

    @Override
    public String toString() {
        return "FluentTermReference: {\n" +
                "\t\t\ttermName: \"" + this.functionName + "\"\n" +
                "\t\t\targuments: " + this.arguments + "\n" +
                "\t\t}";
    }
}
