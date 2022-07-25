package net.quickwrite.fluent4j.util.args;

import net.quickwrite.fluent4j.FluentBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FluentArgs {
    private final Map<String, FluentArgument<?>> namedArguments;
    private final List<FluentArgument<?>> positionalArguments;

    public FluentArgs() {
        this.namedArguments = new HashMap<>();
        this.positionalArguments = new ArrayList<>();
    }

    public FluentArgument<?> getNamed(final String key) {
        return this.namedArguments.get(key);
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

    public <T> FluentArgument<T> getOrDefault(String key, T defaultValue) {
        FluentArgument<?> argument = namedArguments.get(key);

        if (argument == null || argument.valueOf().getClass() != defaultValue.getClass()) {
            return new FluentArgument<T>() {
                @Override
                public T valueOf() {
                    return defaultValue;
                }

                @Override
                public boolean matches(FluentArgument<?> selector) {
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
}
