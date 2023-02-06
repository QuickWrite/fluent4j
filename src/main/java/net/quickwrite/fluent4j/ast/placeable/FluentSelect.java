package net.quickwrite.fluent4j.ast.placeable;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.FluentResolvable;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.container.FluentScope;

import java.util.function.Function;

public interface FluentSelect extends FluentPlaceable {
    interface FluentVariant extends FluentResolvable {
        FluentIdentifier<FluentVariantKey> getIdentifier();

        interface FluentVariantKey extends FluentPattern {

        }
    }

    interface Selectable {
        Function<FluentVariant, Boolean> selectChecker(final FluentScope scope);
    }
}
