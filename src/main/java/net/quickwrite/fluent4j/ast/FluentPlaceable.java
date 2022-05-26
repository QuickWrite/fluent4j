package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.exception.FluentParseException;
import net.quickwrite.fluent4j.parser.StringSlice;

import java.util.List;

/**
 * Text in Fluent may use special syntax to incorporate
 * small pieces of programmable interface. Those pieces
 * are denoted with curly braces <code>{</code> and
 * <code>}</code> are called placeables.
 *
 * <p>
 *     It's common to use placeables to interpolate external
 *     variables into the translation. Variable values are
 *     provided by the developer and they will be set on runtime.
 *     They may also dynamically change as the user uses the
 *     localized product.
 *     <pre>
 *         # $title (String) - The title of the bookmark to remove.
 *         remove-bookmark = Really remove { $title }?
 *     </pre>
 * </p>
 * <p>
 *     It's also possible to interpolate other messages and terms
 *     inside of text values.
 *     <pre>
 *         -brand-name = Firefox
 *         installing = Installing { -brand-name }.
 *     </pre>
 * </p>
 * <p>
 *     Lastly, placeables can be used to insert special characters
 *     into text values. For instance, due to placeables using <code>{</code>
 *     and <code>}</code> as delimiters, inserting a literal curly
 *     brace into the translation requires special care. Quoted
 *     text can be effectively used for the purpose:
 *     <pre>
 *         opening-brace = This message features an opening curly brace: {"{"}.
 *         closing-brace = This message features a closing curly brace: {"}"}.
 *     </pre>
 * </p>
 */
public abstract class FluentPlaceable extends FluentElement {

    /**
     * Returns the content the placeable uses inside.
     *
     * @return content
     */
    public abstract StringSlice getContent();

    /**
     * The string literal is the simplest of the Placeables
     * as it is just a raw String where everything can be done
     * without the parser changing anything.
     *
     * <p>
     *     So for example when there needs to be leading whitespace
     *     in a message there can a StringLiteral be used at the front:
     *
     *     <pre>
     *         blank-is-preserved = {"    "}This message starts with 4 spaces.
     *     </pre>
     *
     *     This wouldn't be the case without the StringLiteral as the
     *     parser directly removes these:
     *
     *     <pre>
     *         blank-is-removed =     This message starts with no blanks.
     *     </pre>
     * </p>
     */
    public static class StringLiteral extends FluentPlaceable {
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
     *
     * For instance, if the current user's name is Jane and she has
     * 5 unread emails, the above translations will be displayed as:
     *
     * <pre>
     *     Welcome, Jane!
     *     Jane has 5 unread emails.
     * </pre>
     */
    public static class VariableReference extends FluentPlaceable {
        protected final StringSlice content;

        public VariableReference(StringSlice content) {
            this.content = content;
        }

        public StringSlice getContent() {
            return this.content;
        }

        @Override
        public String toString() {
            return "FluentMessageReference: {\n" +
                    "\t\t\tcontent: \"" + this.content + "\"\n" +
                    "\t\t}";
        }
    }

    /**
     * A use-case for placeables is referencing one message in another one.
     *
     * <pre>
     *     menu-save = Save
     *     help-menu-save = Click { menu-save } to save the file.
     * </pre>
     *
     * Referencing other messages generally helps to keep certain translations
     * consistent across the interface and makes maintenance easier.
     */
    public static class MessageReference extends VariableReference {
        public MessageReference(StringSlice content) {
            super(content);
        }

        @Override
        public String toString() {
            return "FluentVariableReference: {\n" +
                    "\t\t\tcontent: \"" + this.content + "\"\n" +
                    "\t\t}";
        }
    }

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
     *
     * FTL has the select expression syntax which allows to define
     * multiple variants of the translation and choose between them
     * based on the value of the selector. The * indicator identifies
     * the default variant. A default variant is required.
     */
    public static class SelectExpression extends FluentPlaceable {
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
        public String toString() {
            return "FluentSelectExpression: {\n" +
                    "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                    "\t\t\tvariants: " + this.variants + "\n" +
                    "\t\t}";
        }
    }

    /**
     * Functions provide additional functionality available to the localizers.
     * They can be either used to format data according to the current language's
     * rules or can provide additional data that the localizer may use (like, the
     * platform, or time of the day) to fine tune the translation.
     */
    public static class FunctionReference extends FluentPlaceable {
        protected final StringSlice functionName;
        protected final StringSlice content;

        public FunctionReference(StringSlice functionName, StringSlice content) {
            this.functionName = functionName;
            if (!check(functionName)) {
                // TODO: Better Error handling
                throw new FluentParseException("The callee has to be an upper-case identifier or a term");
            }

            this.content = content;
        }

        public StringSlice getContent() {
            return this.content;
        }

        protected boolean check(StringSlice string) {
            while (!string.isBigger()) {
                if(!Character.isUpperCase(string.getChar())) {
                    return false;
                }

                string.increment();
            }

            string.setIndex(0);

            return true;
        }

        @Override
        public String toString() {
            return "FluentFunctionReference: {\n" +
                    "\t\t\tfunctionName: \"" + this.functionName + "\"\n" +
                    "\t\t\tcontent: \"" + this.content + "\"\n" +
                    "\t\t}";
        }
    }

    /**
     * Terms are similar to regular messages but they can
     * only be used as references in other messages. Their
     * identifiers start with a single dash {@code -}
     * like in the example: {@code -brand-name}. The
     * runtime cannot retrieve terms directly. They are best
     * used to define vocabulary and glossary items which
     * can be used consistently across the localization
     * of the entire product.
     *
     * <pre>
     *     -brand-name = Firefox
     *
     *     about = About { -brand-name }.
     *     update-successful = { -brand-name } has been updated.
     * </pre>
      */
    public static class TermReference extends FunctionReference {
        public TermReference(StringSlice functionName, StringSlice content) {
            super(functionName, content);
        }

        @Override
        protected boolean check(StringSlice string) {
            return true;
        }

        @Override
        public String toString() {
            return "FluentTermReference: {\n" +
                    "\t\t\ttermName: \"" + this.functionName + "\"\n" +
                    "\t\t\tcontent: \"" + this.content + "\"\n" +
                    "\t\t}";
        }
    }
}
