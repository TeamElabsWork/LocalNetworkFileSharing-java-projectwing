package com.fileshare.transfer;

import com.fileshare.contracts.port.TransferEngine;
import com.fileshare.contracts.registry.BackendContext;
import com.fileshare.contracts.registry.ComponentRegistry;

import java.util.List;
import java.util.function.Function;

/** Registry of transfer engines, selected by {@code backend.engine} in config/pipeline.properties. */
public final class TransferEngineRegistry {
    private static final ComponentRegistry<TransferEngine> REGISTRY = new ComponentRegistry<>("Transfer engine");

    private TransferEngineRegistry() {}

    public static void register(String name, Function<BackendContext, TransferEngine> factory) {
        REGISTRY.register(name, factory);
    }

    public static TransferEngine get(String name, BackendContext ctx) { return REGISTRY.get(name, ctx); }

    public static List<String> listAvailable() { return REGISTRY.listAvailable(); }

    public static void reset() { REGISTRY.reset(); }

    /** Registers "simulated" (default) and "network". Called explicitly by the application. */
    public static void registerDefaults() {
        Function<BackendContext, TransferEngine> simulated = ctx ->
                new SimulatedTransferEngine(ctx.executor(), ctx.files(), ctx.transfers(), ctx.intOption("simulated.step_ms", 150));
        register("simulated", simulated);
        register("default", simulated);
        register("network", ctx ->
                new NetworkTransferEngine(ctx.config(), ctx.executor(), ctx.transfers(), ctx.files(), ctx.transport()));
    }
}
