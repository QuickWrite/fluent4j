package net.quickwrite.fluent4j.impl.ast.pattern.container;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentNumberLiteral;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentTextElement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluentArgumentContainer implements ArgumentList {
    private final Map<String, NamedArgument> named;
    private final List<FluentPattern> positional;

    private FluentArgumentContainer(final Map<String, NamedArgument> named, final List<FluentPattern> positional) {
        this.named = named;
        this.positional = positional;
    }

    @Override
    public NamedArgument getArgument(final String name) {
        return this.named.get(name);
    }

    public void addArgument(final String name, final NamedArgument argument) {
        this.named.put(name, argument);
    }

    @Override
    public FluentPattern getArgument(final int index) {
        return this.positional.get(index);
    }

    public void addAttribute(final FluentPattern pattern) {
        this.positional.add(pattern);
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
            return new FluentArgumentContainer(named, List.of());
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
            return new FluentArgumentContainer(named, positional);
        }
    }
}
