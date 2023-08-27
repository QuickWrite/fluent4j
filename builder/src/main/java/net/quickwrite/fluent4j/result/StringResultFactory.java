package net.quickwrite.fluent4j.result;

import net.quickwrite.fluent4j.impl.result.StringResultBuilder;

public final class StringResultFactory {
    public static ResultBuilder construct() {
        return new StringResultBuilder();
    }
}
