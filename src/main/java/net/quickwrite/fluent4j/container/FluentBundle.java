package net.quickwrite.fluent4j.container;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentFunction;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface FluentBundle<B extends ResultBuilder> {
    void addResource(final FluentResource<B> resource);

    void addResourceOverriding(final FluentResource<B> resource);

    boolean hasMessage(final String key);

    Set<Map.Entry<String, FluentEntry<B>>> getMessages();

    Optional<FluentEntry<B>> getMessage(final String key);

    Optional<B> resolveMessage(final String key, final ArgumentList<B> argumentList, final B builder);

    <T extends FluentEntry<B>> Set<Map.Entry<String, FluentEntry<B>>> getEntries(final Class<T> clazz);

    <T extends FluentEntry<B>> Optional<T> getEntry(final String key, final Class<T> clazz);

    ULocale getLocale();

    Optional<FluentFunction<B>> getFunction(final String key);

    Set<FluentFunction<B>> getFunctions();
}
