package net.quickwrite.fluent4j.util;

import net.quickwrite.fluent4j.functions.AbstractFunction;

import java.util.HashMap;
import java.util.Map;

public class BuiltinFunctions {
    private static Map<String, AbstractFunction> BUILTIN_FUNCTIONS;

    static {
        BUILTIN_FUNCTIONS = new HashMap<>();
    }

    private BuiltinFunctions() {

    }

    /**
     * Returns a copy of the builtin functions.
     *
     * @return A {@link Map} of {@link AbstractFunction}s.
     * @noinspection rawtypes
     */
    @SuppressWarnings("unchecked")
    public static Map<String, AbstractFunction> getBuiltinFunctions() {
        return (Map<String, AbstractFunction>) ((HashMap)BUILTIN_FUNCTIONS).clone();
    }
}
