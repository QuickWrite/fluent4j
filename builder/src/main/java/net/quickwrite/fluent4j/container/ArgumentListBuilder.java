package net.quickwrite.fluent4j.container;

import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.impl.ast.pattern.container.FluentArgumentContainer;
import net.quickwrite.fluent4j.result.ResultBuilder;

public class ArgumentListBuilder {
    static <B extends ResultBuilder> ArgumentList<B> empty() {
        return ArgumentList.empty();
    }

    static <B extends ResultBuilder> ArgumentList.Builder<B> builder() {
        return FluentArgumentContainer.builder();
    }

    static <B extends ResultBuilder> ArgumentList.PlenaryBuilder<B> plenaryBuilder() {
        return FluentArgumentContainer.plenaryBuilder();
    }
}
