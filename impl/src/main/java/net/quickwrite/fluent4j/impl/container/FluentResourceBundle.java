package net.quickwrite.fluent4j.impl.container;

import com.ibm.icu.util.ULocale;
import net.quickwrite.fluent4j.ast.FluentFunction;
import net.quickwrite.fluent4j.ast.entry.FluentEntry;
import net.quickwrite.fluent4j.ast.entry.FluentMessage;
import net.quickwrite.fluent4j.ast.pattern.ArgumentList;
import net.quickwrite.fluent4j.container.FluentBundle;
import net.quickwrite.fluent4j.container.FluentResource;
import net.quickwrite.fluent4j.impl.ast.entry.FluentMessageElement;
import net.quickwrite.fluent4j.impl.function.NumberFunction;
import net.quickwrite.fluent4j.result.ResultBuilder;

import java.util.*;

public class FluentResourceBundle implements FluentBundle {
    private final ULocale locale;
    private final Map<Class<? extends FluentEntry>, Map<String, FluentEntry>> entries;

    private final Map<String, FluentFunction> functions;

    private FluentResourceBundle(
            final ULocale locale,
            final Map<Class<? extends FluentEntry>, Map<String, FluentEntry>> entries,
            final Map<String, FluentFunction> functions
    ) {
        this.locale = locale;
        this.entries = entries;

        this.functions = functions;
    }

    @Override
    public boolean hasMessage(final String key) {
        final Optional<Map<String, FluentEntry>> entryMap = getEntryMap(FluentMessageElement.class);

        return entryMap.map(fluentEntryMap -> fluentEntryMap.containsKey(key)).orElse(false);
    }

    @Override
    public Set<FluentMessage> getMessages() {
        return getEntries(FluentMessage.class);
    }

    @Override
    public Optional<FluentMessage> getMessage(final String key) {
        final Optional<Map<String, FluentEntry>> entryMap = getEntryMap(FluentMessage.class);

        return entryMap.map(fluentEntryMap -> (FluentMessage) fluentEntryMap.get(key));
    }

    @Override
    public Optional<ResultBuilder> resolveMessage(final String key, final ArgumentList argumentList, final ResultBuilder builder) {
        final Optional<FluentMessage> message = getMessage(key);

        if (message.isEmpty()) {
            return Optional.empty();
        }

        message.get().resolve(new FluentResolverScope(this, argumentList, builder), builder);

        return Optional.of(builder);
    }

    @Override
    public Optional<ResultBuilder> resolveMessage(final String key, final ResultBuilder builder) {
        final Optional<FluentMessage> message = getMessage(key);

        if (message.isEmpty()) {
            return Optional.empty();
        }

        message.get().resolve(new FluentResolverScope(this, ArgumentList.empty(), builder), builder);

        return Optional.of(builder);
    }

    @Override
    public <T extends FluentEntry> Set<T> getEntries(final Class<T> clazz) {
        final Optional<Map<String, FluentEntry>> entryMap = getEntryMap(clazz);

        if (entryMap.isEmpty()) {
            return Set.of();
        }

        return new Set<>() {
            private final Collection<FluentEntry> entries = entryMap.get().values();

            @Override
            public int size() {
                return entries.size();
            }

            @Override
            public boolean isEmpty() {
                return entries.isEmpty();
            }

            @Override
            public boolean contains(final Object o) {
                return entries.contains(o);
            }

            @SuppressWarnings("unchecked")
            @Override
            public Iterator<T> iterator() {
                return (Iterator<T>) entries.iterator();
            }

            @Override
            public Object[] toArray() {
                return entries.toArray();
            }

            @Override
            public <T1> T1[] toArray(final T1[] a) {
                return entries.toArray(a);
            }

            @Override
            public boolean add(final T bFluentEntry) {
                return false;
            }

            @Override
            public boolean remove(final Object o) {
                return false;
            }

            @Override
            public boolean containsAll(final Collection<?> c) {
                return entries.containsAll(c);
            }

            @Override
            public boolean addAll(final Collection<? extends T> c) {
                return false;
            }

            @Override
            public boolean retainAll(final Collection<?> c) {
                return false;
            }

            @Override
            public boolean removeAll(final Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {
            }
        };
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends FluentEntry> Optional<T> getEntry(final String key, final Class<T> clazz) {
        final Optional<Map<String, FluentEntry>> entryMap = getEntryMap(clazz);

        return entryMap.map(fluentEntryMap -> (T) fluentEntryMap.get(key));
    }

    private <T> Optional<Map<String, FluentEntry>> getEntryMap(final Class<T> clazz) {
        return Optional.ofNullable(this.entries.get(clazz));
    }

    @Override
    public ULocale getLocale() {
        return this.locale;
    }

    @Override
    public Optional<FluentFunction> getFunction(final String key) {
        return Optional.ofNullable(this.functions.get(key));
    }

    @Override
    public Set<FluentFunction> getFunctions() {
        return new HashSet<>(this.functions.values());
    }

    public static FluentBundle.Builder builder(final ULocale locale) {
        return new FluentResourceBundle.FluentResourceBundleBuilder(locale);
    }

    private static class FluentResourceBundleBuilder implements FluentBundle.Builder {
        private final ULocale locale;
        private final Map<Class<? extends FluentEntry>, Map<String, FluentEntry>> entries;
        private final Map<String, FluentFunction> functions;

        public FluentResourceBundleBuilder(final ULocale locale) {
            this.locale = locale;
            this.entries = new HashMap<>();
            this.functions = new HashMap<>();
        }

        @Override
        public Builder addResource(final FluentResource resource) {
            for (final FluentEntry entry : resource.entries()) {
                final Class<? extends FluentEntry> clazz = getClass(entry);

                if (!entries.containsKey(clazz)) {
                    entries.put(clazz, new HashMap<>());
                }

                entries.get(clazz).put(entry.getIdentifier().getSimpleIdentifier(), entry);
            }
            return this;
        }

        @Override
        public Builder addResourceNoDup(final FluentResource resource) {
            for (final FluentEntry entry : resource.entries()) {
                final Class<? extends FluentEntry> clazz = getClass(entry);

                if (!entries.containsKey(clazz)) {
                    entries.put(clazz, new HashMap<>());
                }

                final Map<String, FluentEntry> innerEntryMap = entries.get(clazz);

                if (innerEntryMap.containsKey(entry.getIdentifier().getSimpleIdentifier())) {
                    throw new RuntimeException("Duplicate entries for key '" + entry.getIdentifier().getFullIdentifier() + "' in '" + clazz.getSimpleName() + "'");
                }

                innerEntryMap.put(entry.getIdentifier().getSimpleIdentifier(), entry);
            }

            return this;
        }

        @Override
        public Builder addFunction(final FluentFunction function) {
            this.functions.put(function.getIdentifier(), function);

            return this;
        }

        @Override
        public Builder addDefaultFunctions() {
            this.addFunction(NumberFunction.getDefault());

            return this;
        }

        @Override
        public FluentBundle build() {
            return new FluentResourceBundle(this.locale, this.entries, this.functions);
        }

        private Class<? extends FluentEntry> getClass(final FluentEntry entry) {
            if (entry instanceof FluentMessage) {
                return FluentMessage.class;
            }

            return entry.getClass();
        }
    }
}
