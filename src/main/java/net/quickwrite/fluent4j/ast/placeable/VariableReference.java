package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.placeable.base.FluentArgumentResult;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;

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
public class VariableReference implements FluentPlaceable<String>, FluentSelectable, FluentArgumentResult {
    private final String content;
    private FluentArgument<?> cache = null;

    public VariableReference(StringSlice content) {
        this.content = content.toString();
    }

    public StringSlice getContent() {
        return new StringSlice(this.content);
    }

    @Override
    public String valueOf() {
        return this.content;
    }

    @Override
    public boolean matches(final FluentBundle bundle, final FluentArgument<?> selector) {
        return selector.valueOf().toString().equals(content);
    }

    @Override
    public String stringValue() {
        return content;
    }

    @Override
    public FluentArgument<?> getArgumentResult(FluentBundle bundle, final FluentArgs arguments) {
        final FluentArgument<?> argument = arguments.getNamed(content);

        if (argument == null) {
            return new StringLiteral("{$" + content + "}");
        }

        return arguments.getNamed(content);
    }

    @Override
    public String getResult(final FluentBundle bundle, final FluentArgs arguments) {
        final FluentArgument<?> argument = arguments.getNamed(content);

        if (argument == null) {
            return "{$" + content + "}";
        }

        return argument.getResult(bundle, arguments);
    }

    @Override
    public String toString() {
        return "FluentVariableReference: {\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t}";
    }
}
