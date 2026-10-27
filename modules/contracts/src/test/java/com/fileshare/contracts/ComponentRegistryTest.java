package com.fileshare.contracts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fileshare.contracts.registry.ComponentRegistry;
import org.junit.jupiter.api.Test;

class ComponentRegistryTest {

    @Test
    void returnsRegisteredBackendCaseInsensitively() {
        ComponentRegistry<String> registry = new ComponentRegistry<>("Thing");
        registry.register("Mock", ctx -> "mock-instance");
        assertEquals("mock-instance", registry.get("mOCK", null));
    }

    @Test
    void unknownNameListsAvailableBackends() {
        ComponentRegistry<String> registry = new ComponentRegistry<>("Thing");
        registry.register("a", ctx -> "x");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> registry.get("b", null));
        assertTrue(e.getMessage().contains("Available"));
    }
}
