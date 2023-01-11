package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.FluentElement;
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
    public CharSequence getResult(final AccessorBundle bundle, final int recursionDepth) {
        return bundle.getBundle()
                .getTerm(this.functionName,
                        new AccessorElementsBundle(bundle.getBundle(),
                        this.getArguments(bundle, recursionDepth)),
                        recursionDepth - 1
                )
                .orElse("{-" + this.functionName + "}");
    }

    /**
     * @param bundle    The bundle that this is being called from
     * @param recursionDepth The amount of recursive calls that can still be made
     * @return The result of the term that is being called
     */
    @Override
    public FluentElement getArgumentResult(final AccessorBundle bundle, final int recursionDepth) {
        return new StringLiteral(getResult(bundle, recursionDepth - 1).toString());
    }

    @Override
    public String toString() {
        return "FluentTermReference: {\n" +
                "\t\t\ttermName: \"" + this.functionName + "\"\n" +
                "\t\t\targuments: " + this.arguments + "\n" +
                "\t\t}";
    }
}
