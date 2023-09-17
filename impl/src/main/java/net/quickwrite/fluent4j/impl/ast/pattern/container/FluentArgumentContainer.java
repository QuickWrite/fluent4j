package net.quickwrite.fluent4j.impl.ast.pattern.container;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentNumberLiteral;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentTextElement;

import java.math.BigDecimal;
import java.util.*;

public class FluentArgumentContainer implements ArgumentList {
    private final Map<String, NamedArgument> named;
    private final FluentPattern[] positional;

    private FluentArgumentContainer(final Map<String, NamedArgument> named, final FluentPattern[] positional) {
        this.named = named;
        this.positional = positional;
    }

    @Override
    public Optional<NamedArgument> getArgument(final String name) {
        return Optional.ofNullable(this.named.get(name));
    }

    @Override
    public Optional<FluentPattern> getArgument(final int index) {
        return Optional.ofNullable(this.positional[index]);
    }

    public static ArgumentList.Builder builder() {
        return new FluentArgumentContainerBuilder();
    }

    public static ArgumentList.PlenaryBuilder plenaryBuilder() {
        return new PlenaryFluentArgumentContainerBuilder();
    }

    private static class FluentArgumentContainerBuilder implements ArgumentList.Builder {
        protected final Map<String, NamedArgument> named;

        public FluentArgumentContainerBuilder() {
            this.named = new HashMap<>();
        }

        @Override
        public Builder add(final String name, final NamedArgument argument) {
            this.named.put(name, argument);

            return this;
        }

        @Override
        public Builder add(final String name, final BigDecimal number) {
            return add(name, new FluentNumberLiteral(number));
        }

        @Override
        public Builder add(final String name, final long number) {
            return add(name, new FluentNumberLiteral(number));
        }

        @Override
        public Builder add(final String name, final double number) {
            return add(name, new FluentNumberLiteral(number));
        }

        @Override
        public Builder add(final String name, final String input) {
            return add(name, new FluentTextElement(input));
        }

        @Override
        public ArgumentList build() {
            return new FluentArgumentContainer(named, new FluentPattern[0]);
        }
    }

    private static final class PlenaryFluentArgumentContainerBuilder
            extends FluentArgumentContainerBuilder
            implements ArgumentList.PlenaryBuilder {
        private final List<FluentPattern> positional;

        public PlenaryFluentArgumentContainerBuilder() {
            super();

            this.positional = new ArrayList<>();
        }

        @Override
        public Builder add(final FluentPattern argument) {
            this.positional.add(argument);

            return this;
        }

        @Override
        public Builder add(final BigDecimal number) {
            return add(new FluentNumberLiteral(number));
        }

        @Override
        public Builder add(final long number) {
            return add(new FluentNumberLiteral(number));
        }

        @Override
        public Builder add(final double number) {
            return add(new FluentNumberLiteral(number));
        }

        @Override
        public Builder add(final String input) {
            return add(new FluentTextElement(input));
        }

        @Override
        public ArgumentList build() {
            return new FluentArgumentContainer(named, positional.toArray(new FluentPattern[0]));
        }
    }
}
