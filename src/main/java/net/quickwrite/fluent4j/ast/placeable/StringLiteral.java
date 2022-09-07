package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.placeable.base.FluentPlaceable;
import net.quickwrite.fluent4j.ast.placeable.base.FluentSelectable;
import net.quickwrite.fluent4j.ast.placeable.base.FluentUnicodeTranslator;
import net.quickwrite.fluent4j.util.StringSlice;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;
import org.apache.commons.text.translate.AggregateTranslator;
import org.apache.commons.text.translate.CharSequenceTranslator;
import org.apache.commons.text.translate.LookupTranslator;

import java.util.Map;

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
 *     blank-is-preserved = {"    "}This message starts with 4 spaces.
 * </pre>
 * <p>
 * This wouldn't be the case without the StringLiteral as the
 * parser directly removes these:
 *
 * <pre>
 *     blank-is-removed =     This message starts with no blanks.
 * </pre>
 */
public class StringLiteral implements FluentPlaceable, FluentSelectable {

    public static final CharSequenceTranslator UNESCAPE_FLUENT;

    static {
        UNESCAPE_FLUENT = new AggregateTranslator(
                new FluentUnicodeTranslator(),
                new LookupTranslator(Map.of("\\\\", "\\", "\\\"", "\""))
        );
    }

    private final String literal;

    public StringLiteral(final StringSlice content) {
        this(content.toString());
    }

    public StringLiteral(final String content) {
        this.literal = getLiteral(content);
    }

    @Override
    public CharSequence getResult(final FluentBundle bundle, final FluentArgs arguments) {
        return this.literal;
    }

    private String getLiteral(final String content) {
        return UNESCAPE_FLUENT.translate(content);
    }

    @Override
    public boolean matches(final FluentBundle bundle, final FluentArgument selector) {
        return this.literal.equals(selector.stringValue());
    }

    @Override
    public String stringValue() {
        return this.literal;
    }

    @Override
    public String toString() {
        return "FluentStringLiteral: {\n" +
                "\t\t\tliteral: \"" + this.literal + "\"\n" +
                "\t\t}";
    }
}
