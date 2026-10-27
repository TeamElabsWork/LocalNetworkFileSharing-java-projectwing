package com.fileshare.ui.fx;

import com.fileshare.contracts.port.UiServices;
import javafx.application.Application;

/**
 * Starts the JavaFX UI with the given services. JavaFX instantiates the Application class itself, so the services
 * are handed over through a static field set right before {@code Application.launch}.
 */
public final class FxLauncher {
    private static volatile UiServices services;

    private FxLauncher() {}

    /** Blocks until the window is closed. */
    public static void launch(UiServices uiServices, String... args) {
        services = uiServices;
        Application.launch(FileShareFxApp.class, args);
    }

    static UiServices services() {
        return services;
    }
}
