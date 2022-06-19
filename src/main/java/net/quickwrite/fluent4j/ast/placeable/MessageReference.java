package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.util.StringSlice;

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
    private final StringSlice content;

    public MessageReference(StringSlice content) {
        this.content = content;
    }

    @Override
    public StringSlice getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "FluentMessageReference: {\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t}";
    }
}
