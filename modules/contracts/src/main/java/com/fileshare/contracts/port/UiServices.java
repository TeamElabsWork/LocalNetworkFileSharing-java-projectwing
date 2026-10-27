package com.fileshare.contracts.port;

import com.fileshare.contracts.config.AppConfig;

/** Everything a user interface (CLI, JavaFX) is allowed to use. Built by the application module. */
public record UiServices(AppConfig config, PeerDirectory peers, TransferEngine engine, TransferStore transfers) {
}
