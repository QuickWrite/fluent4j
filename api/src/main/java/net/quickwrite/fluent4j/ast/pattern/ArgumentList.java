package net.quickwrite.fluent4j.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.math.BigDecimal;

public interface ArgumentList<B extends ResultBuilder> {
    NamedArgument<B> getArgument(final String name);
    FluentPattern<B> getArgument(final int index);

    @SuppressWarnings("unchecked")
    static <B extends ResultBuilder> ArgumentList<B> empty() {
        return (ArgumentList<B>) ImmutableArgumentList.SELF;
    }

    interface Builder<B extends ResultBuilder> extends net.quickwrite.fluent4j.util.Builder<ArgumentList<B>> {
        Builder<B> add(final String name, final NamedArgument<B> argument);

        Builder<B> add(final String name, final BigDecimal number);

        Builder<B> add(final String name, final long number);

        Builder<B> add(final String name, final double number);

        Builder<B> add(final String name, final String input);
    }

    interface PlenaryBuilder<B extends ResultBuilder> extends Builder<B> {

        Builder<B> add(final FluentPattern<B> argument);

        Builder<B> add(final BigDecimal number);

        Builder<B> add(final long number);

        Builder<B> add(final double number);

        Builder<B> add(final String input);
    }

    interface NamedArgument<B extends ResultBuilder> extends FluentPattern<B> {

    }
}

class ImmutableArgumentList<B extends ResultBuilder> implements ArgumentList<B> {
    public static final ImmutableArgumentList<? extends ResultBuilder> SELF = new ImmutableArgumentList<>();

    @Override
    public NamedArgument<B> getArgument(final String name) {
        return null;
    }

    @Override
    public FluentPattern<B> getArgument(final int index) {
        return null;
    }
}
