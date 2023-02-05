package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.FluentResolvable;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.container.FluentScope;

public interface FluentSelect extends FluentPlaceable {
    interface FluentVariant extends FluentResolvable {
        FluentIdentifier getIdentifier();
    }

    interface Selectable {
        boolean selectCheck(final FluentScope scope, final FluentVariant variant);

        default void endSelect() {

        }
    }
}
