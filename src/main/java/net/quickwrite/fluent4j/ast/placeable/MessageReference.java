package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;

/**
 * A use-case for placeables is referencing one message in another one.
 *
 * <pre>
 *     menu-save = Save
 *     help-menu-save = Click { menu-save } to save the file.
 * </pre>
 * <p>
 * Referencing other messages generally helps to keep certain translations
 * consistent across the interface and makes maintenance easier.
 */
public class MessageReference implements FluentPlaceable<StringSlice> {
    private final StringSlice content;

    public MessageReference(StringSlice content) {
        this.content = content;
    }

    @Override
    public StringSlice getContent() {
        return this.content;
    }

    @Override
    public StringSlice valueOf() {
        return this.content;
    }

    @Override
    public boolean matches(FluentArgument<?> selector) {
        return selector.valueOf().toString().equals(this.content.toString());
    }

    @Override
    public String stringValue() {
        return this.content.toString();
    }

    @Override
    public String getResult(final FluentBundle bundle, final FluentArgs arguments) {
        return bundle.getMessage(this.content.toString()).getResult(bundle, arguments);
    }

    @Override
    public String toString() {
        return "FluentMessageReference: {\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t}";
    }
}
