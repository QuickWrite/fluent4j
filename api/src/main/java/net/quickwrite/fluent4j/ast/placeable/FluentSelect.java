package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.FluentResolvable;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.container.FluentScope;
import net.quickwrite.fluent4j.result.ResultBuilder;

/**
 * The Select Placeable that can be used as a Placeable in
 * Fluent Documents.
 *
 * <p>
 *     The default version of fluent has this type of Select Expression where
 *     there are different Variants and a main selectable element that is being
 *     switched upon.
 *     <pre>
 *         select = { $var ->
 *              [hi] Hello
 *              *[bye] Goodbye
 *         }
 *     </pre>
 * </p>
 */
public interface FluentSelect extends FluentPlaceable {
    /**
     * A single variant of the Select Placeable
     */
    interface FluentVariant extends FluentResolvable {
        FluentIdentifier<FluentVariantKey> getIdentifier();

        interface FluentVariantKey extends FluentPattern {
        }
    }

    /**
     * Defines if the element is selectable and how it should
     * react to the different values.
     */
    interface Selectable {
        /**
         * Selects the best variant that is being given.
         *
         * <p>
         *     This method does <strong>not</strong> return
         *     {@code null} and should always return the
         *     default variant if something either went wrong
         *     (in a recoverable way) or there are no other
         *     options.
         * </p>
         *
         * @param scope The scope that is being used for this resolve
         * @param variants The different variants
         * @param defaultVariant A default variant that can always be
         *                       used as a fallback
         * @return The best variant for this element
         */
        FluentVariant select(final FluentScope scope, final FluentVariant[] variants, final FluentVariant defaultVariant);
    }
}
