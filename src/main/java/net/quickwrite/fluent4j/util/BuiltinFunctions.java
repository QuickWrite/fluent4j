package net.quickwrite.fluent4j.util;

import net.quickwrite.fluent4j.util.bundle.ResourceFluentBundle;
import net.quickwrite.fluent4j.functions.AbstractFunction;
import net.quickwrite.fluent4j.functions.NumberFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * A helper class that stores all of the functions that
 * are added to every single {@link ResourceFluentBundle}.
 */
public class BuiltinFunctions {
    private static final Map<String, AbstractFunction> BUILTIN_FUNCTIONS;

    static {
        BUILTIN_FUNCTIONS = new HashMap<>();
        NumberFunction numberFunction = new NumberFunction();
        BUILTIN_FUNCTIONS.put(numberFunction.getIdentifier(), numberFunction);
    }

    private BuiltinFunctions() {

    }

    /**
     * Returns a copy of the builtin functions.
     *
     * @return A {@link Map} of {@link AbstractFunction}s.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, AbstractFunction> getBuiltinFunctions() {
        return (Map<String, AbstractFunction>) ((HashMap<?,?>) BUILTIN_FUNCTIONS).clone();
    }
}
