package net.quickwrite.fluent4j.ast.pattern;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentNumberLiteral;
import net.quickwrite.fluent4j.impl.ast.pattern.FluentTextElement;
import net.quickwrite.fluent4j.impl.ast.pattern.container.FluentArgumentContainer;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.math.BigDecimal;

public interface ArgumentList<B extends ResultBuilder> {
    NamedArgument<B> getArgument(final String name);
    FluentPattern<B> getArgument(final int index);

    @SuppressWarnings("unchecked")
    static <B extends ResultBuilder> ArgumentList<B> empty() {
        return (ArgumentList<B>) FluentArgumentContainer.EMPTY;
    }

    static <B extends ResultBuilder> ArgumentList.Builder<B> builder() {
        return FluentArgumentContainer.builder();
    }

    static <B extends ResultBuilder> ArgumentList.PlenaryBuilder<B> plenaryBuilder() {
        return FluentArgumentContainer.plenaryBuilder();
    }

    interface Builder<B extends ResultBuilder> extends net.quickwrite.fluent4j.util.Builder<ArgumentList<B>> {
        Builder<B> add(final String name, final NamedArgument<B> argument);

        default Builder<B> add(final String name, final BigDecimal number) {
            return add(name, new FluentNumberLiteral<>(number));
        }

        default Builder<B> add(final String name, final long number) {
            return add(name, new FluentNumberLiteral<>(number));
        }

        default Builder<B> add(final String name, final double number) {
            return add(name, new FluentNumberLiteral<>(number));
        }

        default Builder<B> add(final String name, final String input) {
            return add(name, new FluentTextElement<>(input));
        }
    }

    interface PlenaryBuilder<B extends ResultBuilder> extends Builder<B> {

        Builder<B> add(final FluentPattern<B> argument);

        default Builder<B> add(final BigDecimal number) {
            return add(new FluentNumberLiteral<>(number));
        }

        default Builder<B> add(final long number) {
            return add(new FluentNumberLiteral<>(number));
        }

        default Builder<B> add(final double number) {
            return add(new FluentNumberLiteral<>(number));
        }

        default Builder<B> add(final String input) {
            return add(new FluentTextElement<>(input));
        }
    }

    interface NamedArgument<B extends ResultBuilder> extends FluentPattern<B> {

    }
}
