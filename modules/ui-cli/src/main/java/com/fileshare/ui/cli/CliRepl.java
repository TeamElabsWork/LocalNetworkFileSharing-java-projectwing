package com.fileshare.ui.cli;

import com.fileshare.contracts.model.FileMetadata;
import com.fileshare.contracts.model.Peer;
import com.fileshare.contracts.model.PeerStatus;
import com.fileshare.contracts.model.TransferSession;
import com.fileshare.contracts.port.ApprovalDecision;
import com.fileshare.contracts.port.UiServices;
import com.fileshare.contracts.util.FormatUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Interactive command-line interface. Working reference UI: shows how a UI uses ONLY {@link UiServices}
 * (peers, engine, transfers) - the JavaFX UI follows the same rules.
 *
 * <pre>
 * help | peers | send &lt;peer# or name&gt; &lt;file&gt; | transfers | cancel &lt;transfer#&gt; | incoming | quit
 * </pre>
 */
public class CliRepl {

    private final UiServices services;
    private final BufferedReader in;
    private final PrintStream out;
    private final Map<String, String> lastPrinted = new ConcurrentHashMap<>();

    public CliRepl(UiServices services, BufferedReader in, PrintStream out) {
        this.services = services;
        this.in = in;
        this.out = out;
    }

    /** Runs until "quit" or end of input; on end of input waits (max 30 s) for running transfers to finish. */
    public void run() throws IOException {
        services.transfers().addListener(this::printChange);
        services.engine().setIncomingRequestHandler((file, sender) -> prompt(file, sender));
        out.println("Local Network File Sharing - CLI   (device: " + services.config().deviceName() + ")");
        out.println("Type 'help' for commands.");
        String line;
        while (true) {
            out.print("> ");
            out.flush();
            line = in.readLine();
            if (line == null) {
                out.println();
                awaitTransfers();
                return;
            }
            if (!handle(line.trim())) {
                return;
            }
        }
    }

    /** Handles one command line. Returns false when the user wants to quit. */
    boolean handle(String line) {
        if (line.isEmpty()) return true;
        String[] parts = line.split("\\s+", 3);
        String cmd = parts[0].toLowerCase(Locale.ROOT);
        switch (cmd) {
            case "help" -> help();
            case "peers" -> peers();
            case "send" -> send(parts);
            case "transfers" -> transfers();
            case "cancel" -> cancel(parts);
            case "incoming" -> incoming();
            case "quit", "exit" -> {
                return false;
            }
            default -> out.println("Unknown command '" + cmd + "'. Type 'help'.");
        }
        return true;
    }

    private void help() {
        out.println("  peers                     list discovered devices");
        out.println("  send <peer# | name> <file> send a file to a device");
        out.println("  transfers                 list transfers with progress");
        out.println("  cancel <transfer#>        cancel a running transfer");
        out.println("  incoming                  simulate a peer sending us a file (demo hook)");
        out.println("  quit                      leave");
    }

    private void peers() {
        List<Peer> peers = services.peers().snapshot();
        if (peers.isEmpty()) {
            out.println("No devices found yet.");
            return;
        }
        int i = 1;
        for (Peer p : peers) {
            out.printf("  %d. %-14s %-15s port %d  %s%n", i++, p.deviceName(), p.address().getHostAddress(), p.tcpPort(), p.status());
        }
    }

    private void send(String[] parts) {
        if (parts.length < 3) {
            out.println("Usage: send <peer# | name> <file>");
            return;
        }
        Peer peer = findPeer(parts[1]);
        if (peer == null) {
            out.println("No such device: " + parts[1] + " (see 'peers')");
            return;
        }
        if (peer.status() != PeerStatus.ONLINE) {
            out.println(peer.deviceName() + " is OFFLINE.");
            return;
        }
        try {
            TransferSession s = services.engine().send(peer, Path.of(parts[2]));
            out.println("Started transfer " + shortId(s) + " of '" + s.file().fileName() + "' to " + peer.deviceName());
        } catch (IOException | RuntimeException e) {
            out.println("Could not send: " + e.getMessage());
        }
    }

    private void transfers() {
        List<TransferSession> all = services.transfers().all();
        if (all.isEmpty()) {
            out.println("No transfers yet.");
            return;
        }
        int i = 1;
        for (TransferSession s : all) {
            out.printf("  %d. %-8s %-9s %-24s %-10s %5.1f%%  %s%n", i++, s.direction(), shortId(s), s.file().fileName(),
                    s.status(), s.progress().percentage(), FormatUtils.formatBytes(s.file().sizeBytes()));
        }
    }

    private void cancel(String[] parts) {
        if (parts.length < 2) {
            out.println("Usage: cancel <transfer#>");
            return;
        }
        List<TransferSession> all = services.transfers().all();
        try {
            int n = Integer.parseInt(parts[1]);
            if (n < 1 || n > all.size()) throw new NumberFormatException();
            services.engine().cancel(all.get(n - 1).transferId());
        } catch (NumberFormatException e) {
            out.println("No such transfer: " + parts[1] + " (see 'transfers')");
        }
    }

    private void incoming() {
        Peer from = services.peers().snapshot().stream().filter(p -> p.status() == PeerStatus.ONLINE).findFirst().orElse(null);
        if (from == null) {
            out.println("No ONLINE device to simulate a request from.");
            return;
        }
        FileMetadata meta = new FileMetadata(UUID.randomUUID().toString(), "holiday-photos.zip", 5L * 1024 * 1024, "c".repeat(64));
        try {
            services.engine().injectIncomingRequest(meta, from);
        } catch (UnsupportedOperationException e) {
            out.println("This engine does not support simulated incoming requests.");
        }
    }

    /** The IncomingRequestHandler of the CLI: asks y/n on the same input (called on the command thread). */
    private ApprovalDecision prompt(FileMetadata file, java.net.InetAddress sender) {
        out.printf("%n[INCOMING] %s wants to send '%s' (%s). Accept? [y/N] ", sender.getHostAddress(), file.fileName(),
                FormatUtils.formatBytes(file.sizeBytes()));
        out.flush();
        try {
            String answer = in.readLine();
            if (answer != null && answer.trim().equalsIgnoreCase("y")) {
                return ApprovalDecision.accept(services.config().downloadDir());
            }
        } catch (IOException e) {
            // fall through: treat as reject
        }
        return ApprovalDecision.reject("Rejected by user");
    }

    /** Prints a line when a transfer's status changes or it crosses a 25 % boundary. */
    private void printChange(TransferSession s) {
        String key = s.status() + ":" + (int) (s.progress().percentage() / 25);
        if (!key.equals(lastPrinted.put(s.transferId(), key))) {
            String reason = s.message().map(m -> " (" + m + ")").orElse("");
            out.printf("[TRANSFER %s] %s '%s' - %s %.0f%%%s%n", shortId(s), s.direction(), s.file().fileName(), s.status(),
                    s.progress().percentage(), reason);
        }
    }

    private void awaitTransfers() {
        long end = System.currentTimeMillis() + 30_000;
        while (!services.transfers().active().isEmpty() && System.currentTimeMillis() < end) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private Peer findPeer(String ref) {
        List<Peer> peers = services.peers().snapshot();
        try {
            int n = Integer.parseInt(ref);
            return n >= 1 && n <= peers.size() ? peers.get(n - 1) : null;
        } catch (NumberFormatException e) {
            return peers.stream().filter(p -> p.deviceName().equalsIgnoreCase(ref)).findFirst().orElse(null);
        }
    }

    private static String shortId(TransferSession s) {
        return s.transferId().substring(0, Math.min(8, s.transferId().length()));
    }
}
