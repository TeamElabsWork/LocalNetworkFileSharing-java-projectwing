package com.fileshare.file;

import com.fileshare.contracts.port.FileService;
import com.fileshare.contracts.registry.BackendContext;
import com.fileshare.contracts.registry.ComponentRegistry;

import java.util.List;
import java.util.function.Function;

/** Registry of file-service backends, selected by {@code backend.files} in config/pipeline.properties. */
public final class FileServiceRegistry {
    private static final ComponentRegistry<FileService> REGISTRY = new ComponentRegistry<>("File service backend");

    private FileServiceRegistry() {}

    public static void register(String name, Function<BackendContext, FileService> factory) {
        REGISTRY.register(name, factory);
    }

    public static FileService get(String name, BackendContext ctx) { return REGISTRY.get(name, ctx); }

    public static List<String> listAvailable() { return REGISTRY.listAvailable(); }

    public static void reset() { REGISTRY.reset(); }

    /** Registers "stub" (default) and "local". Called explicitly by the application. */
    public static void registerDefaults() {
        register("stub", ctx -> new StubFileService());
        register("default", ctx -> new StubFileService());
        register("local", ctx -> new LocalFileService(ctx.config()));
    }
}
