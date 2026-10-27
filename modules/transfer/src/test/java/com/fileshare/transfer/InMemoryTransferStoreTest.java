package com.fileshare.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fileshare.contracts.model.FileMetadata;
import com.fileshare.contracts.model.TransferDirection;
import com.fileshare.contracts.model.TransferSession;
import com.fileshare.contracts.model.TransferStatus;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class InMemoryTransferStoreTest {

    private static TransferSession session(String id) {
        return new TransferSession(new FileMetadata(id, "f.txt", 1, "b".repeat(64)), null, TransferDirection.UPLOAD);
    }

    @Test
    void registersFindsAndFiltersActive() {
        InMemoryTransferStore store = new InMemoryTransferStore();
        TransferSession a = session("a");
        TransferSession b = session("b");
        store.register(a);
        store.register(b);
        b.setStatus(TransferStatus.COMPLETED);
        assertEquals(2, store.all().size());
        assertEquals(1, store.active().size());
        assertTrue(store.find("a").isPresent());
        assertTrue(store.find("zzz").isEmpty());
    }

    @Test
    void listenersAreNotifiedAndCannotBreakTheStore() {
        InMemoryTransferStore store = new InMemoryTransferStore();
        AtomicInteger calls = new AtomicInteger();
        store.addListener(s -> { throw new IllegalStateException("boom"); });
        store.addListener(s -> calls.incrementAndGet());
        store.register(session("x"));
        assertEquals(1, calls.get());
    }
}
