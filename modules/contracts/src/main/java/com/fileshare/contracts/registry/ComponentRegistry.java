package com.fileshare.contracts.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Tiny name -> factory registry used by every pluggable component (discovery, transport, files, engine).
 * Backends are selected by name from config/pipeline.properties. No registration on class loading - modules expose
 * a {@code registerDefaults()} method that the application calls explicitly.
 */
public final class ComponentRegistry<T> {

    private final String kind;
    private final Map<String, Function<BackendContext, T>> factories = new ConcurrentHashMap<>();

    public ComponentRegistry(String kind) {
        this.kind = kind;
    }

    public void register(String name, Function<BackendContext, T> factory) {
        factories.put(name.toLowerCase(Locale.ROOT), factory);
    }

    public T get(String name, BackendContext context) {
        Function<BackendContext, T> f = factories.get(name.toLowerCase(Locale.ROOT));
        if (f == null) {
            throw new IllegalArgumentException(
                    kind + " '" + name + "' is not registered. Available: " + listAvailable());
        }
        return f.apply(context);
    }

    public List<String> listAvailable() {
        return new ArrayList<>(factories.keySet());
    }

    public void reset() {
        factories.clear();
    }
}
