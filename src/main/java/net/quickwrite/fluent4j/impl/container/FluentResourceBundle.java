package net.quickwrite.fluent4j.impl.container;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.ast.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentFunction;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.impl.ast.entry.FluentMessage;

import java.io.IOException;
import java.util.*;

public class FluentResourceBundle implements FluentBundle {
    private final ULocale locale;
    private final Map<Class<? extends FluentEntry>, Map<String, FluentEntry>> entries;

    private final Map<String, FluentFunction> functions;

    public FluentResourceBundle(final ULocale locale) {
        this.locale = locale;
        this.entries = new HashMap<>();

        this.functions = new HashMap<>();
    }

    @Override
    public void addResource(final FluentResource resource) {
        for (final FluentEntry entry : resource.entries()) {
            final Class<? extends FluentEntry> clazz = entry.getClass();

            if (!entries.containsKey(clazz)) {
                entries.put(clazz, new HashMap<>());
            }

            final Map<String, FluentEntry> innerEntryMap = entries.get(clazz);

            if (innerEntryMap.containsKey(entry.getIdentifier().getSimpleIdentifier())) {
                throw new RuntimeException("Duplicate entries for key '" + entry.getIdentifier().getFullIdentifier() + "' in '" + clazz.getSimpleName() + "'");
            }

            innerEntryMap.put(entry.getIdentifier().getSimpleIdentifier(), entry);
        }
    }

    @Override
    public void addResourceOverriding(final FluentResource resource) {
        for (final FluentEntry entry : resource.entries()) {
            final Class<? extends FluentEntry> clazz = entry.getClass();

            if (!entries.containsKey(clazz)) {
                entries.put(clazz, new HashMap<>());
            }

            entries.get(clazz).put(entry.getIdentifier().getSimpleIdentifier(), entry);
        }
    }

    @Override
    public boolean hasMessage(final String key) {
        return this.entries.get(FluentMessage.class).containsKey(key);
    }

    @Override
    public Set<Map.Entry<String, FluentEntry>> getMessages() {
        return getEntries(FluentMessage.class);
    }

    @Override
    public Optional<FluentEntry> getMessage(final String key) {
        return Optional.ofNullable(this.entries.get(FluentMessage.class).get(key));
    }

    @Override
    public Optional<String> resolveMessage(final String key, final ArgumentList argumentList) {
        final Optional<FluentEntry> message = getMessage(key);

        if (message.isEmpty()) {
            return Optional.empty();
        }

        final StringBuilder builder = new StringBuilder();

        try {
            message.get().resolve(new FluentResolverScope(this, argumentList), builder);
        } catch (final IOException ignored) {
            return Optional.empty();
        }

        return Optional.of(builder.toString());
    }

    @Override
    public <T extends FluentEntry> Set<Map.Entry<String, FluentEntry>> getEntries(final Class<T> clazz) {
        return entries.get(clazz).entrySet();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends FluentEntry> Optional<T> getEntry(final String key, final Class<T> clazz) {
        return Optional.ofNullable((T) this.entries.get(clazz).get(key));
    }

    @Override
    public ULocale getLocale() {
        return this.locale;
    }

    @Override
    public FluentFunction getFunction(String key) {
        return null;
    }

    @Override
    public Set<FluentFunction> getFunctions() {
        return null;
    }
}
