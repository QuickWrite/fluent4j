package net.quickwrite.fluent4j.ast.entry;

import net.quickwrite.fluent4j.ast.FluentPattern;
import net.quickwrite.fluent4j.ast.FluentResolvable;
import net.quickwrite.fluent4j.ast.identifier.FluentIdentifier;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.List;
import java.util.Optional;

public interface FluentEntry<B extends ResultBuilder> extends FluentResolvable<B>, FluentPattern<B> {
    FluentIdentifier<String> getIdentifier();
    List<FluentEntry.Attribute<B>> getAttributes();

    default Optional<FluentEntry.Attribute<B>> getAttribute(final String identifier) {
        for (final FluentEntry.Attribute<B> attribute : getAttributes()) {
            if(attribute.getIdentifier().getSimpleIdentifier().equals(identifier)) {
                return Optional.of(attribute);
            }
        }

        return Optional.empty();
    }

    interface Attribute<B extends ResultBuilder> extends FluentResolvable<B> {
        FluentIdentifier<String> getIdentifier();

        List<FluentPattern<B>> getPatterns();
    }
}
