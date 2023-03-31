package net.quickwrite.fluent4j.result;

import net.quickwrite.fluent4j.impl.result.StringResultBuilder;

public final class StringResultFactory implements ResultBuilderFactory {
    @Override
    public ResultBuilder create() {
        return construct();
    }

    public static ResultBuilder construct() {
        return new StringResultBuilder();
    }
}
