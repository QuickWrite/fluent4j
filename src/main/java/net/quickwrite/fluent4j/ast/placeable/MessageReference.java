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
public class MessageReference implements FluentPlaceable {
    private final String reference;

    public MessageReference(final StringSlice reference) {
        this.reference = reference.toString();
    }

    @Override
    public boolean matches(final FluentBundle bundle, final FluentArgument selector) {
        return selector.stringValue().equals(this.reference);
    }

    @Override
    public String stringValue() {
        return this.reference;
    }

    @Override
    public String getResult(final FluentBundle bundle, final FluentArgs arguments) {
        return bundle.getMessage(this.stringValue(), arguments);
    }

    @Override
    public String toString() {
        return "FluentMessageReference: {\n" +
                "\t\t\treference: \"" + this.reference + "\"\n" +
                "\t\t}";
    }
}
