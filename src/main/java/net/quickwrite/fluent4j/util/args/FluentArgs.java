package net.quickwrite.fluent4j.util.args;

import net.quickwrite.fluent4j.FluentBundle;
import net.quickwrite.fluent4j.ast.placeable.base.FluentArgumentResult;

import java.util.*;

public class FluentArgs {
    private final Map<String, FluentArgument<?>> namedArguments;
    private final List<FluentArgument<?>> positionalArguments;

    public FluentArgs() {
        this(new HashMap<>(), new ArrayList<>());
    }

    public FluentArgs(Map<String, FluentArgument<?>> namedArguments, List<FluentArgument<?>> positionalArguments) {
        this.namedArguments = namedArguments;
        this.positionalArguments = positionalArguments;
    }

    public FluentArgument<?> getNamed(final String key) {
        return this.namedArguments.get(key);
    }

    public void sanitize(final FluentBundle bundle, final FluentArgs arguments) {
        for (String key : namedArguments.keySet()) {
            final FluentArgument<?> argument = namedArguments.get(key);

            if (argument instanceof FluentArgumentResult) {
                namedArguments.put(key, ((FluentArgumentResult) argument).getArgumentResult(bundle, arguments));
            }
        }

        for (int i = 0; i < positionalArguments.size(); i++) {
            final FluentArgument<?> argument = positionalArguments.get(i);

            if (argument instanceof FluentArgumentResult) {
                positionalArguments.set(i, ((FluentArgumentResult) argument).getArgumentResult(bundle, arguments));
            }
        }
    }

    public FluentArgument<?> getPositional(final int index) {
        return this.positionalArguments.get(index);
    }

    public void setNamed(final String key, final FluentArgument<?> argument) {
        this.namedArguments.put(key, argument);
    }

    public void addPositional(final FluentArgument<?> argument) {
        this.positionalArguments.add(argument);
    }

    @SuppressWarnings("unchecked")
    public <T> FluentArgument<T> getOrDefault(String key, T defaultValue, Class<T> clazz) {
        FluentArgument<?> argument = namedArguments.get(key);

        if (argument == null || !clazz.isInstance(argument.valueOf())) {
            return new FluentArgument<T>() {
                @Override
                public T valueOf() {
                    return defaultValue;
                }

                @Override
                public boolean matches(final FluentBundle bundle, final FluentArgument<?> selector) {
                    return selector.valueOf().equals(defaultValue);
                }

                @Override
                public String stringValue() {
                    return defaultValue.toString();
                }

                @Override
                public String getResult(FluentBundle bundle, FluentArgs arguments) {
                    return stringValue();
                }

                @Override
                public String toString() {
                    return stringValue();
                }
            };
        }

        return (FluentArgument<T>) argument;
    }

    @Override
    public String toString() {
        return "FluentArgumentList: {\n" +
                "\t\t\tnamedArguments: \"" + this.namedArguments + "\"\n" +
                "\t\t\tpositionalArguments: \"" + this.positionalArguments + "\"\n" +
                "\t\t}";
    }
}
