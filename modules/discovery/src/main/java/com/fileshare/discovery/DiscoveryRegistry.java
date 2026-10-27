package com.fileshare.discovery;

import com.fileshare.contracts.port.DiscoveryService;
import com.fileshare.contracts.registry.BackendContext;
import com.fileshare.contracts.registry.ComponentRegistry;

import java.util.List;

/** Registry of discovery backends, selected by {@code backend.discovery} in config/pipeline.properties. */
public final class DiscoveryRegistry {
    private static final ComponentRegistry<DiscoveryService> REGISTRY = new ComponentRegistry<>("Discovery backend");

    private DiscoveryRegistry() {}

    public static void register(String name, java.util.function.Function<BackendContext, DiscoveryService> factory) {
        REGISTRY.register(name, factory);
    }

    public static DiscoveryService get(String name, BackendContext ctx) { return REGISTRY.get(name, ctx); }

    public static List<String> listAvailable() { return REGISTRY.listAvailable(); }

    public static void reset() { REGISTRY.reset(); }

    /** Registers "mock" (default) and "udp". Called explicitly by the application. */
    public static void registerDefaults() {
        register("mock", ctx -> new MockDiscoveryService(ctx.peers()));
        register("default", ctx -> new MockDiscoveryService(ctx.peers()));
        register("udp", ctx -> new UdpDiscoveryService(ctx.config(), ctx.peers(), ctx.executor()));
    }
}
