package net.quickwrite.fluent4j.container;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.ast.FluentEntry;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface FluentBundle {
    void addResource(final FluentResource resource);

    void addResourceOverriding(final FluentResource resource);

    boolean hasMessage(final String key);

    Set<Map.Entry<String, FluentEntry>> getMessages();

    Optional<FluentEntry> getMessage(final String key);

    Optional<String> resolveMessage(final String key, final ArgumentList argumentList);

    <T extends FluentEntry> Set<Map.Entry<String, FluentEntry>> getEntries(final Class<T> clazz);

    <T extends FluentEntry> Optional<T> getEntry(final String key, final Class<T> clazz);

    ULocale getLocale();
}
