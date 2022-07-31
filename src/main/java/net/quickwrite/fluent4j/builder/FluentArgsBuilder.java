package net.quickwrite.fluent4j.builder;

import net.quickwrite.fluent4j.ast.placeable.NumberLiteral;
import net.quickwrite.fluent4j.ast.placeable.StringLiteral;
import net.quickwrite.fluent4j.util.args.FluentArgs;
import net.quickwrite.fluent4j.util.args.FluentArgument;

public class FluentArgsBuilder extends AbstractBuilder<FluentArgs> {
    public FluentArgsBuilder() {
        super(new FluentArgs());
    }

    public FluentArgsBuilder setNamed(final String key, final FluentArgument<?> argument) {
        this.element.setNamed(key, argument);

        return this;
    }

    public FluentArgsBuilder setNamed(final String key, final String argument) {
        return this.setNamed(key, new StringLiteral(argument));
    }

    public FluentArgsBuilder setNamed(final String key, final Number argument) {
        return this.setNamed(key, new NumberLiteral(argument));
    }

    public FluentArgsBuilder addPositional(final FluentArgument<?> argument) {
        this.element.addPositional(argument);

        return this;
    }

    public FluentArgsBuilder addPositional(final String argument) {
        return this.addPositional(new StringLiteral(argument));
    }

    public FluentArgsBuilder addPositional(final Number argument) {
        return this.addPositional(new NumberLiteral(argument));
    }
}
