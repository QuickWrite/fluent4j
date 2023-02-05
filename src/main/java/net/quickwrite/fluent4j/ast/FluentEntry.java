package net.quickwrite.fluent4j.ast;

import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;

import java.util.List;
import java.util.Optional;

public interface FluentEntry extends FluentResolvable {

    FluentIdentifier getIdentifier();
    List<FluentEntry.Attribute> getAttributes();

    default Optional<FluentEntry.Attribute> getAttribute(final String identifier) {
        for (final FluentEntry.Attribute attribute : getAttributes()) {
            if(attribute.getIdentifier().getSimpleIdentifier().equals(identifier)) {
                return Optional.of(attribute);
            }
        }

        return Optional.empty();
    }

    interface Attribute extends FluentResolvable {
        FluentIdentifier getIdentifier();

        List<FluentPattern> getPatterns();
    }
}
