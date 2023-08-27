package net.quickwrite.fluent4j.container;

import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.impl.ast.pattern.container.FluentArgumentContainer;

public class ArgumentListBuilder {
    private ArgumentListBuilder() {}

    public static ArgumentList empty() {
        return ArgumentList.empty();
    }

    public static ArgumentList.Builder builder() {
        return FluentArgumentContainer.builder();
    }

    public static ArgumentList.PlenaryBuilder plenaryBuilder() {
        return FluentArgumentContainer.plenaryBuilder();
    }
}
