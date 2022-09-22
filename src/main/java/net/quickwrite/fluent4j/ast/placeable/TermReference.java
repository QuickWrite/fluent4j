package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.ast.FluentTerm;
import net.quickwrite.fluent4j.ast.placeable.base.FluentFunction;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.bundle.DirectFluentBundle;

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
    public CharSequence getResult(final DirectFluentBundle bundle, final FluentArgs arguments) {
        return this.getArgumentResult(bundle, arguments).getResult(bundle, this.getArguments(bundle, arguments));
    }

    /**
     * @param bundle    The bundle that this is being called from
     * @param arguments The arguments that are passed into this function
     * @return
     */
    @Override
    public FluentElement getArgumentResult(final DirectFluentBundle bundle, final FluentArgs arguments) {
        final FluentTerm term = bundle.getTerm(this.functionName);

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
