package com.fileshare.network;

import com.fileshare.contracts.port.TransferTransport;
import com.fileshare.contracts.registry.BackendContext;
import com.fileshare.contracts.registry.ComponentRegistry;

import java.util.List;
import java.util.function.Function;

/** Registry of transport backends, selected by {@code backend.transport} in config/pipeline.properties. */
public final class TransportRegistry {
    private static final ComponentRegistry<TransferTransport> REGISTRY = new ComponentRegistry<>("Transport backend");

    private TransportRegistry() {}

    public static void register(String name, Function<BackendContext, TransferTransport> factory) {
        REGISTRY.register(name, factory);
    }

    public static TransferTransport get(String name, BackendContext ctx) { return REGISTRY.get(name, ctx); }

    public static List<String> listAvailable() { return REGISTRY.listAvailable(); }

    public static void reset() { REGISTRY.reset(); }

    /** Registers "loopback" (default) and "tcp". Called explicitly by the application. */
    public static void registerDefaults() {
        register("loopback", ctx -> new LoopbackTransport(LoopbackNetwork.shared(), ctx.executor(), ctx.config().tcpPort()));
        register("default", ctx -> new LoopbackTransport(LoopbackNetwork.shared(), ctx.executor(), ctx.config().tcpPort()));
        register("tcp", ctx -> new TcpTransport(ctx.config(), ctx.executor()));
    }
}
