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
public class StringLiteral implements FluentPlaceable<String>, FluentSelectable {

    public static final CharSequenceTranslator UNESCAPE_FLUENT;

    static {
        UNESCAPE_FLUENT = new AggregateTranslator(
                new FluentUnicodeTranslator(),
                new LookupTranslator(Map.of("\\\\", "\\", "\\\"", "\""))
        );
    }

    private final StringSlice content;
    private final String literal;

    public StringLiteral(StringSlice content) {
        this.content = content;
        this.literal = getLiteral();
    }

    public StringLiteral(String content) {
        this.content = new StringSlice(content);
        this.literal = content;
    }

    public StringSlice getContent() {
        return this.content;
    }

    @Override
    public String getResult(final FluentBundle bundle, final FluentArgs arguments) {
        return this.literal;
    }

    private String getLiteral() {
        return UNESCAPE_FLUENT.translate(content.toString());
    }

    @Override
    public String valueOf() {
        return this.literal;
    }

    @Override
    public boolean matches(final FluentBundle bundle, final FluentArgument<?> selector) {
        return this.literal.equals(selector.stringValue());
    }

    @Override
    public String stringValue() {
        return this.literal;
    }

    @Override
    public String toString() {
        return "FluentStringLiteral: {\n" +
                "\t\t\tcontent: \"" + this.content + "\"\n" +
                "\t\t\tliteral: \"" + this.literal + "\"\n" +
                "\t\t}";
    }
}
