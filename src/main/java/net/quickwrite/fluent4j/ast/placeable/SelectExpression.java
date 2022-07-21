package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.FluentVariant;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;

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
    private final FluentPlaceable identifier;

    public SelectExpression(FluentPlaceable identifier, List<FluentVariant> variants) {
        this.identifier = identifier;
        this.variants = variants;
    }

    public StringSlice getContent() {
        return this.identifier.getContent();
    }

    @Override
    public String getResult(final FluentBundle bundle, final FluentArgs arguments) {
        // TODO: Implement
        return null;
    }

    @Override
    public String toString() {
        return "FluentSelectExpression: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tvariants: " + this.variants + "\n" +
                "\t\t}";
    }
}
