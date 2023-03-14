package net.quickwrite.fluent4j.impl.ast.pattern.container;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentNumberLiteral;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentTextElement;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluentArgumentContainer<B extends ResultBuilder> implements ArgumentList<B> {
    public static ArgumentList<? extends ResultBuilder> EMPTY = builder().build();

    private final Map<String, NamedArgument<B>> named;
    private final List<FluentPattern<B>> positional;

    private FluentArgumentContainer(final Map<String, NamedArgument<B>> named, final List<FluentPattern<B>> positional) {
        this.named = named;
        this.positional = positional;
    }

    @Override
    public NamedArgument<B> getArgument(final String name) {
        return this.named.get(name);
    }

    public void addArgument(final String name, final NamedArgument<B> argument) {
        this.named.put(name, argument);
    }

    @Override
    public FluentPattern<B> getArgument(final int index) {
        return this.positional.get(index);
    }

    public void addAttribute(final FluentPattern<B> pattern) {
        this.positional.add(pattern);
    }

    public static <B extends ResultBuilder> ArgumentList.Builder<B> builder() {
        return new FluentArgumentContainerBuilder<>();
    }

    public static <B extends ResultBuilder> ArgumentList.PlenaryBuilder<B> plenaryBuilder() {
        return new PlenaryFluentArgumentContainerBuilder<>();
    }

    private static class FluentArgumentContainerBuilder<B extends ResultBuilder> implements ArgumentList.Builder<B> {
        protected final Map<String, NamedArgument<B>> named;

        public FluentArgumentContainerBuilder() {
            this.named = new HashMap<>();
        }

        @Override
        public Builder<B> add(final String name, final NamedArgument<B> argument) {
            this.named.put(name, argument);

            return this;
        }

        @Override
        public Builder<B> add(final String name, final BigDecimal number) {
            return add(name, new FluentNumberLiteral<>(number));
        }

        @Override
        public Builder<B> add(final String name, final long number) {
            return add(name, new FluentNumberLiteral<>(number));
        }

        @Override
        public Builder<B> add(final String name, final double number) {
            return add(name, new FluentNumberLiteral<>(number));
        }

        @Override
        public Builder<B> add(final String name, final String input) {
            return add(name, new FluentTextElement<>(input));
        }

        @Override
        public ArgumentList<B> build() {
            return new FluentArgumentContainer<>(named, List.of());
        }
    }

    private static final class PlenaryFluentArgumentContainerBuilder<B extends ResultBuilder>
            extends FluentArgumentContainerBuilder<B>
            implements ArgumentList.PlenaryBuilder<B>
    {
        private final List<FluentPattern<B>> positional;

        public PlenaryFluentArgumentContainerBuilder() {
            super();

            this.positional = new ArrayList<>();
        }

        @Override
        public Builder<B> add(final FluentPattern<B> argument) {
            this.positional.add(argument);

            return this;
        }

        @Override
        public Builder<B> add(final BigDecimal number) {
            return add(new FluentNumberLiteral<>(number));
        }

        @Override
        public Builder<B> add(final long number) {
            return add(new FluentNumberLiteral<>(number));
        }

        @Override
        public Builder<B> add(final double number) {
            return add(new FluentNumberLiteral<>(number));
        }

        @Override
        public Builder<B> add(final String input) {
            return add(new FluentTextElement<>(input));
        }

        @Override
        public ArgumentList<B> build() {
            return new FluentArgumentContainer<>(named, positional);
        }
    }
}
