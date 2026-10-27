package com.fileshare.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fileshare.contracts.model.Peer;
import com.fileshare.contracts.model.PeerStatus;
import com.fileshare.contracts.model.TransferSession;
import com.fileshare.contracts.model.TransferStatus;
import com.fileshare.ui.cli.CliRepl;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** End-to-end smoke test using only the default mock backends (the app-level equivalent of "make demo"). */
class PipelineSmokeTest {

    @TempDir
    Path dir;

    private static Properties fast() {
        Properties p = new Properties();
        p.setProperty("simulated.step_ms", "0");
        return p;
    }

    @Test
    void mockPipelineDiscoversPeersAndCompletesATransfer() throws Exception {
        try (AppContext ctx = PipelineFactory.create(fast())) {
            ctx.start();
            assertEquals(3, ctx.peers().snapshot().size());
            Peer online = ctx.peers().snapshot().stream().filter(p -> p.status() == PeerStatus.ONLINE).findFirst().orElseThrow();

            Path file = dir.resolve("demo.txt");
            Files.writeString(file, "hello network");
            TransferSession session = ctx.engine().send(online, file);

            long end = System.currentTimeMillis() + 5000;
            while (!session.status().isTerminal() && System.currentTimeMillis() < end) {
                Thread.sleep(5);
            }
            assertEquals(TransferStatus.COMPLETED, session.status());
            assertEquals(1, ctx.transfers().all().size());
        }
    }

    @Test
    void cliDemoRunsEndToEnd() throws Exception {
        Path file = dir.resolve("photo.jpg");
        Files.write(file, new byte[4096]);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (AppContext ctx = PipelineFactory.create(fast())) {
            ctx.start();
            String script = "peers\nsend Laptop-B " + file + "\n"; // end of input => waits for the transfer
            new CliRepl(ctx.uiServices(), new BufferedReader(new StringReader(script)), new PrintStream(bytes, true)).run();
        }
        String out = bytes.toString();
        assertTrue(out.contains("Laptop-B"));
        assertTrue(out.contains("COMPLETED"), out);
    }

    @Test
    void unsetBackendsFallBackToDefaults() {
        Properties p = fast();
        p.setProperty("backend.discovery", "unset");
        try (AppContext ctx = PipelineFactory.create(p)) {
            assertTrue(ctx.engine() != null);
        }
    }

    @Test
    void unknownBackendNameFailsWithHelpfulMessage() {
        Properties p = fast();
        p.setProperty("backend.engine", "does-not-exist");
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> PipelineFactory.create(p));
        assertTrue(e.getMessage().contains("Available"));
    }

    @Test
    void readsPipelinePropertiesFromAConfigDirectory() throws Exception {
        Files.writeString(dir.resolve("pipeline.properties"), "backend.discovery=mock\nsimulated.step_ms=0\n");
        try (AppContext ctx = PipelineFactory.create(dir)) {
            ctx.start();
            assertEquals(3, ctx.peers().snapshot().size());
        }
    }
}
