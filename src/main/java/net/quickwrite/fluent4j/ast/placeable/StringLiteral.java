package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.util.StringSlice;

/**
 * The string literal is the simplest of the Placeables
 * as it is just a raw String where everything can be done
 * without the parser changing anything.
 *
 * <p>
 * So for example when there needs to be leading whitespace
 * in a message there can a StringLiteral be used at the front:
 *
 * <pre>
 *         blank-is-preserved = {"    "}This message starts with 4 spaces.
 *     </pre>
 * <p>
 * This wouldn't be the case without the StringLiteral as the
 * parser directly removes these:
 *
 * <pre>
 *         blank-is-removed =     This message starts with no blanks.
 *     </pre>
 * </p>
 */
public class StringLiteral implements FluentPlaceable, FluentSelectable {
    private final StringSlice content;

    public StringLiteral(StringSlice content) {
        this.content = content;
    }

    public StringSlice getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        return "FluentStringLiteral: {\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t}";
    }
}
