package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.placeable.base.FluentFunction;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;

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
    public TermReference(StringSlice name) {
        super(name, null);
    }

    public TermReference(StringSlice name, StringSlice content) {
        super(name, content);
    }

    @Override
    protected boolean check(StringSlice string) {
        return true;
    }

    @Override
    public String getResult(FluentBundle bundle, FluentArgs arguments) {
        return this.getArgumentResult(bundle).getResult(bundle, this.arguments);
    }

    public FluentArgument<?> getArgumentResult(FluentBundle bundle) {
        return bundle.getTerm(this.functionName.toString());
    }

    @Override
    public String toString() {
        return "FluentTermReference: {\n" +
                "\t\t\ttermName: \"" + this.functionName + "\"\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t\targuments: " + this.arguments + "\n" +
                "\t\t}";
    }
}
