package net.quickwrite.fluent4j.ast.placeable.base;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.util.args.FluentArgument;

/**
 * Text in Fluent may use special syntax to incorporate
 * small pieces of programmable interface. Those pieces
 * are denoted with curly braces <code>{</code> and
 * <code>}</code> are called placeables.
 *
 * <p>
 * It's common to use placeables to interpolate external
 * variables into the translation. Variable values are
 * provided by the developer and they will be set on runtime.
 * They may also dynamically change as the user uses the
 * localized product.
 * <pre>
 *         # $title (String) - The title of the bookmark to remove.
 *         remove-bookmark = Really remove { $title }?
 *     </pre>
 * </p>
 * <p>
 * It's also possible to interpolate other messages and terms
 * inside of text values.
 * <pre>
 *         -brand-name = Firefox
 *         installing = Installing { -brand-name }.
 *     </pre>
 * </p>
 * <p>
 * Lastly, placeables can be used to insert special characters
 * into text values. For instance, due to placeables using <code>{</code>
 * and <code>}</code> as delimiters, inserting a literal curly
 * brace into the translation requires special care. Quoted
 * text can be effectively used for the purpose:
 * <pre>
 *         opening-brace = This message features an opening curly brace: {"{"}.
 *         closing-brace = This message features a closing curly brace: {"}"}.
 *     </pre>
 * </p>
 */
public interface FluentPlaceable extends FluentElement, FluentArgument {
}
