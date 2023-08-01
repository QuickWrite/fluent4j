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
 *
 * @param <B> The scope that the Select Placeable should use
 */
public interface FluentSelect<B extends ResultBuilder> extends FluentPlaceable<B> {
    /**
     * A single variant of the Select Placeable
     *
     * @param <B> The scope that the variant should use
     */
    interface FluentVariant<B extends ResultBuilder> extends FluentResolvable<B> {
        FluentIdentifier<FluentVariantKey<B>> getIdentifier();

        interface FluentVariantKey<B extends ResultBuilder> extends FluentPattern<B> {
        }
    }

    /**
     * Defines if the element is selectable and how it should
     * react to the different values.
     *
     * @param <B> The type of ResultBuilder associated with the resolvable entity.
     */
    interface Selectable<B extends ResultBuilder> {
        /**
         * Returns a select checker that can be used to check if
         * a Variant is the correct variant.
         *
         * @param scope The scope that the select checker should use
         * @return A SelectChecker that can be used for the different variants
         */
        SelectChecker<B> selectChecker(final FluentScope<B> scope);

        /**
         * The checker to check if the current
         * variant is the correct variant in the list.
         *
         * @param <B> The type of ResultBuilder associated with the resolvable entity.
         */
        interface SelectChecker<B extends ResultBuilder> {
            boolean check(final FluentVariant<B> variant);
        }
    }
}
