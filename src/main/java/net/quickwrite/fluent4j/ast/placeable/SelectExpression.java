package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.FluentVariant;
import net.quickwrite.fluent4j.ast.placeable.base.FluentArgumentResult;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;

import java.util.List;

/**
 * One of the most common cases when a localizer needs to use a
 * placeable is when there are multiple variants of the string
 * that depend on some external variable. In the example, the
 * emails message depends on the value of the $unreadEmails variable.
 *
 * <pre>
 *     emails =
 *          { $unreadEmails ->
 *              [one] You have one unread email.
 *             *[other] You have { $unreadEmails } unread emails.
 *          }
 * </pre>
 * <p>
 * FTL has the select expression syntax which allows to define
 * multiple variants of the translation and choose between them
 * based on the value of the selector. The * indicator identifies
 * the default variant. A default variant is required.
 */
public class SelectExpression implements FluentPlaceable {
    private final List<FluentVariant> variants;
    private final FluentVariant defaultVariant;
    private final FluentPlaceable identifier;

    public SelectExpression(FluentPlaceable identifier, List<FluentVariant> variants, FluentVariant defaultVariant) {
        this.identifier = identifier;
        this.variants = variants;
        this.defaultVariant = defaultVariant;
    }

    @Override
    public boolean matches(final FluentBundle bundle, final FluentArgument selector) {
        return false;
    }

    @Override
    public String stringValue() {
        return this.toString();
    }

    @Override
    public CharSequence getResult(final FluentBundle bundle, final FluentArgs arguments) {
        final FluentArgument argument = (identifier instanceof FluentArgumentResult) ?
                ((FluentArgumentResult)identifier).getArgumentResult(bundle, arguments) : identifier;

        for (final FluentVariant variant : variants) {
            if (argument.matches(bundle, variant.getIdentifier())) {
                return variant.getResult(bundle, arguments);
            }
        }

        return defaultVariant.getResult(bundle, arguments);
    }

    @Override
    public String toString() {
        return "FluentSelectExpression: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tvariants: " + this.variants + "\n" +
                "\t\t\tdefault: " + this.defaultVariant + "\n" +
                "\t\t}";
    }
}
