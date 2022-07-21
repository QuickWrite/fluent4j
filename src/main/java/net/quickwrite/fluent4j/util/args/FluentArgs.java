package net.quickwrite.fluent4j.util.args;

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
}
