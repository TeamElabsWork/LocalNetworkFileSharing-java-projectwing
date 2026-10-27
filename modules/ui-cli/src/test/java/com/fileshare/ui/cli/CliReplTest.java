package com.fileshare.ui.cli;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fileshare.contracts.config.AppConfig;
import com.fileshare.contracts.model.FileMetadata;
import com.fileshare.contracts.model.Peer;
import com.fileshare.contracts.model.PeerStatus;
import com.fileshare.contracts.model.TransferDirection;
import com.fileshare.contracts.model.TransferSession;
import com.fileshare.contracts.model.TransferStatus;
import com.fileshare.contracts.port.IncomingRequestHandler;
import com.fileshare.contracts.port.PeerDirectory;
import com.fileshare.contracts.port.TransferEngine;
import com.fileshare.contracts.port.TransferListener;
import com.fileshare.contracts.port.TransferStore;
import com.fileshare.contracts.port.UiServices;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.InetAddress;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class CliReplTest {

    private final List<TransferSession> sessions = new ArrayList<>();
    private final List<TransferListener> listeners = new ArrayList<>();

    private final TransferStore store = new TransferStore() {
        public void register(TransferSession s) { sessions.add(s); }
        public Optional<TransferSession> find(String id) { return sessions.stream().filter(s -> s.transferId().equals(id)).findFirst(); }
        public List<TransferSession> all() { return List.copyOf(sessions); }
        public List<TransferSession> active() { return sessions.stream().filter(s -> !s.status().isTerminal()).toList(); }
        public void publish(TransferSession s) { listeners.forEach(l -> l.onTransferChanged(s)); }
        public void addListener(TransferListener l) { listeners.add(l); }
        public void removeListener(TransferListener l) { listeners.remove(l); }
    };

    private final TransferEngine engine = new TransferEngine() {
        public void start() {}
        public void stop() {}
        public void cancel(String id) {}
        public void setIncomingRequestHandler(IncomingRequestHandler h) {}
        public TransferSession send(Peer peer, Path file) {
            FileMetadata meta = new FileMetadata("abcdef123456", file.getFileName().toString(), 10, "d".repeat(64));
            TransferSession s = new TransferSession(meta, peer, TransferDirection.UPLOAD);
            sessions.add(s);
            s.setStatus(TransferStatus.COMPLETED);
            store.publish(s);
            return s;
        }
    };

    private final PeerDirectory peers = new PeerDirectory() {
        private final Peer p = peer();
        public void upsert(Peer x) {}
        public Optional<Peer> find(String id) { return Optional.of(p); }
        public void remove(String id) {}
        public List<Peer> snapshot() { return List.of(p); }
        public int markStaleOffline(Duration t, Instant n) { return 0; }
    };

    private static Peer peer() {
        try {
            return new Peer("p1", "Laptop-B", InetAddress.getByName("192.168.1.20"), 37021, Instant.now(), PeerStatus.ONLINE);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private String run(String script) throws Exception {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true);
        UiServices services = new UiServices(AppConfig.defaults(), peers, engine, store);
        new CliRepl(services, new BufferedReader(new StringReader(script)), out).run();
        return bytes.toString();
    }

    @Test
    void listsPeersAndSendsFile() throws Exception {
        String output = run("peers\nsend 1 /tmp/report.pdf\ntransfers\nquit\n");
        assertTrue(output.contains("Laptop-B"));
        assertTrue(output.contains("Started transfer"));
        assertTrue(output.contains("report.pdf"));
        assertTrue(output.contains("COMPLETED"));
    }

    @Test
    void unknownCommandAndMissingPeerAreReportedNotThrown() throws Exception {
        String output = run("dance\nsend 9 x.txt\nquit\n");
        assertTrue(output.contains("Unknown command"));
        assertTrue(output.contains("No such device"));
        assertFalse(output.contains("Exception"));
    }
}
