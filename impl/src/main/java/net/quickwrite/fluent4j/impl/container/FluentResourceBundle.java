package net.quickwrite.fluent4j.impl.container;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.ast.FluentFunction;
import net.quickwrite.fluent4j.ast.entry.FluentMessage;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.impl.ast.entry.FluentMessageElement;
import net.quickwrite.fluent4j.impl.function.NumberFunction;
import net.quickwrite.fluent4j.impl.parser.FluentParserGroup;
import net.quickwrite.fluent4j.parser.ResourceParser;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.*;

public class FluentResourceBundle<B extends ResultBuilder> implements FluentBundle<B> {
    private final ULocale locale;
    private final Map<Class<? extends FluentEntry<?>>, Map<String, FluentEntry<B>>> entries;

    private final Map<String, FluentFunction<B>> functions;

    private FluentResourceBundle(
            final ULocale locale,
            final Map<Class<? extends FluentEntry<?>>, Map<String, FluentEntry<B>>> entries,
            final Map<String, FluentFunction<B>> functions
    ) {
        this.locale = locale;
        this.entries = entries;

        this.functions = functions;
    }

    @Override
    public boolean hasMessage(final String key) {
        final Optional<Map<String, FluentEntry<B>>> entryMap = getEntryMap(FluentMessageElement.class);

        return entryMap.map(fluentEntryMap -> fluentEntryMap.containsKey(key)).orElse(false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Map.Entry<String, FluentEntry<B>>> getMessages() {
        return getEntries(FluentMessage.class);
    }

    @Override
    public Optional<FluentEntry<B>> getMessage(final String key) {
        final Optional<Map<String, FluentEntry<B>>> entryMap = getEntryMap(FluentMessage.class);

        return entryMap.map(fluentEntryMap -> fluentEntryMap.get(key));
    }

    @Override
    public Optional<B> resolveMessage(final String key, final ArgumentList<B> argumentList, final B builder) {
        final Optional<FluentEntry<B>> message = getMessage(key);

        if (message.isEmpty()) {
            return Optional.empty();
        }

        message.get().resolve(new FluentResolverScope<>(this, argumentList, builder), builder);

        return Optional.of(builder);
    }

    @Override
    public Optional<B> resolveMessage(final String key, final B builder) {
        final Optional<FluentEntry<B>> message = getMessage(key);

        if (message.isEmpty()) {
            return Optional.empty();
        }

        message.get().resolve(new FluentResolverScope<>(this, ArgumentList.empty(), builder), builder);

        return Optional.of(builder);
    }

    @Override
    public <T extends FluentEntry<B>> Set<Map.Entry<String, FluentEntry<B>>> getEntries(final Class<T> clazz) {
        final Optional<Map<String, FluentEntry<B>>> entryMap = getEntryMap(clazz);

        return entryMap.map(Map::entrySet).orElseGet(Set::of);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends FluentEntry<B>> Optional<T> getEntry(final String key, final Class<T> clazz) {
        final Optional<Map<String, FluentEntry<B>>> entryMap = getEntryMap(clazz);

        return entryMap.map(fluentEntryMap -> (T) fluentEntryMap.get(key));
    }

    private <T> Optional<Map<String, FluentEntry<B>>> getEntryMap(final Class<T> clazz) {
        return Optional.ofNullable(this.entries.get(clazz));
    }

    @Override
    public ULocale getLocale() {
        return this.locale;
    }

    @Override
    public Optional<FluentFunction<B>> getFunction(final String key) {
        return Optional.ofNullable(this.functions.get(key));
    }

    @Override
    public Set<FluentFunction<B>> getFunctions() {
        return new HashSet<>(this.functions.values());
    }

    public static <B extends ResultBuilder> FluentBundle.Builder<B> builder(final ULocale locale) {
        return new FluentResourceBundle.FluentResourceBundleBuilder<>(locale);
    }

    private static class FluentResourceBundleBuilder<B extends ResultBuilder> implements FluentBundle.Builder<B> {
        private final ULocale locale;
        private final Map<Class<? extends FluentEntry<?>>, Map<String, FluentEntry<B>>> entries;
        private final Map<String, FluentFunction<B>> functions;

        public FluentResourceBundleBuilder(final ULocale locale) {
            this.locale = locale;
            this.entries = new HashMap<>();
            this.functions = new HashMap<>();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Builder<B> addResource(final FluentResource<B> resource) {
            for (final FluentEntry<B> entry : resource.entries()) {
                final Class<? extends FluentEntry<?>> clazz = (Class<? extends FluentEntry<?>>) getClass(entry);

                if (!entries.containsKey(clazz)) {
                    entries.put(clazz, new HashMap<>());
                }

                entries.get(clazz).put(entry.getIdentifier().getSimpleIdentifier(), entry);
            }
            return this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Builder<B> addResourceNoDup(final FluentResource<B> resource) {
            for (final FluentEntry<B> entry : resource.entries()) {
                final Class<? extends FluentEntry<?>> clazz = (Class<? extends FluentEntry<?>>) getClass(entry);

                if (!entries.containsKey(clazz)) {
                    entries.put(clazz, new HashMap<>());
                }

                final Map<String, FluentEntry<B>> innerEntryMap = entries.get(clazz);

                if (innerEntryMap.containsKey(entry.getIdentifier().getSimpleIdentifier())) {
                    throw new RuntimeException("Duplicate entries for key '" + entry.getIdentifier().getFullIdentifier() + "' in '" + clazz.getSimpleName() + "'");
                }

                innerEntryMap.put(entry.getIdentifier().getSimpleIdentifier(), entry);
            }

            return this;
        }

        @Override
        public Builder<B> addFunction(final FluentFunction<B> function) {
            this.functions.put(function.getIdentifier(), function);

            return this;
        }

        @Override
        public Builder<B> addDefaultFunctions() {
            this.addFunction(NumberFunction.getDefault());

            return this;
        }

        @Override
        public FluentBundle<B> build() {
            return new FluentResourceBundle<>(this.locale, this.entries, this.functions);
        }

        private Class<? extends FluentEntry> getClass(final FluentEntry<B> entry) {
            if (entry instanceof FluentMessage) {
                return FluentMessage.class;
            }

            return entry.getClass();
        }
    }
}
