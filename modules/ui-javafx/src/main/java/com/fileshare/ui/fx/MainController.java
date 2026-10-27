package com.fileshare.ui.fx;

import com.fileshare.contracts.port.UiServices;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Coordinates the main screen. Baseline: a placeholder view so the app starts.
 * RULES: no network or disk work on the JavaFX thread; update the UI from workers only through Platform.runLater or
 * JavaFX properties; use ONLY the {@link UiServices} (never another module's classes).
 */
public class MainController {

    private final UiServices services;

    public MainController(UiServices services) {
        this.services = services;
    }

    public Parent createView() {
        Label title = new Label("Local Network File Sharing");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        Label device = new Label("This device: " + services.config().deviceName());
        Label peers = new Label("Devices found: " + services.peers().snapshot().size());
        Label todo = new Label("UI skeleton - peer list, send dialog and transfers view come from the UI issues.");
        VBox box = new VBox(12, title, device, peers, todo);
        box.setPadding(new Insets(24));
        return box;
    }
}
