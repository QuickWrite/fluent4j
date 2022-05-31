package net.quickwrite.fluent4j.ast.wrapper;

import net.quickwrite.fluent4j.ast.FluentElement;
import net.quickwrite.fluent4j.ast.FluentPlaceable;
import net.quickwrite.fluent4j.parser.StringSlice;

public class FluentArgument extends FluentElement {
    private final StringSlice identifier;
    private final FluentPlaceable placeable;

    public FluentArgument(StringSlice identifier, FluentPlaceable placeable) {
        this.identifier = identifier;
        this.placeable = placeable;
    }

    public boolean isNamed() {
        return identifier != null;
    }

    @Override
    public String toString() {
        return "FluentArgument: {\n" +
                "\t\t\tidentifier: \"" + this.identifier + "\"\n" +
                "\t\t\tplaceable: " + this.placeable + "\n" +
                "\t\t}";
    }
}
