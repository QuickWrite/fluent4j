package net.quickwrite.fluent4j.impl.ast.pattern;

import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.ast.placeable.FluentSelect;
import net.quickwrite.fluent4j.container.FluentScope;

import java.io.IOException;
import java.util.function.Function;

public class FluentFunction extends ParameterizedLiteral<String> implements FluentSelect.Selectable {

    public FluentFunction(final String identifier, final ArgumentList attributes) {
        super(identifier, attributes);
    }

    @Override
    public void resolve(final FluentScope scope, final Appendable appendable) throws IOException {
        // TODO: Make this possible
    }

    @Override
    public Function<FluentSelect.FluentVariant, Boolean> selectChecker(FluentScope scope) {
        return (variant) -> false;
    }
}
