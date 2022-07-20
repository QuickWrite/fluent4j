package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgumentList;

/**
 * Variables are pieces of data received from the app.
 * They are provided by the developer of the app and
 * may be interpolated into the translation with placeables.
 * Variables can dynamically change as the user is
 * using the localized product. <br>
 * Variables are referred to via the {@code $variable-name} syntax:
 *
 * <pre>
 *     welcome = Welcome, { $user }!
 *     unread-emails = { $user } has { $email-count } unread emails.
 * </pre>
 * <p>
 * For instance, if the current user's name is Jane and she has
 * 5 unread emails, the above translations will be displayed as:
 *
 * <pre>
 *     Welcome, Jane!
 *     Jane has 5 unread emails.
 * </pre>
 */
public class VariableReference implements FluentPlaceable, FluentSelectable {
    private final StringSlice content;

    public VariableReference(StringSlice content) {
        this.content = content;
    }

    public StringSlice getContent() {
        return this.content;
    }

    @Override
    public String getResult(final FluentBundle bundle, final FluentArgumentList arguments) {
        // TODO: Implement
        return null;
    }

    @Override
    public String toString() {
        return "FluentVariableReference: {\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t}";
    }
}
