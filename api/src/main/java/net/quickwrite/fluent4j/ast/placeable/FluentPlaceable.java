package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.result.ResultBuilder;

/**
 * The specific interface fot a placeable.
 *
 * <p>
 *     In the default Fluent implementation a placeable
 *     looks like this:
 *     <pre>
 *        placeable = { $var }
 *     </pre>
 * </p>
 * @param <B> The scope that the placeable should use
 */
public interface FluentPlaceable<B extends ResultBuilder> extends FluentPattern<B> {
    /**
     * A specific interface that is being used to define that
     * this element should behave like a placeable but shouldn't
     * be directly used as a standard placeable: <br />
     *
     * This for example is being used as a way to limit the
     * ability for a standard Fluent Term Attribute Reference
     * to be used as a normal Placeable. Which means that this isn't possible:
     * <pre>
     *     attr-reference = {-test.attribute}
     * </pre>
     */
    interface CannotPlaceable {
        /**
         * Returns the name that should be used for the
         * Exception that is being thrown because this cannot be
         * used as a placeable.
         *
         * @return The name
         */
        String getName();
    }
}
