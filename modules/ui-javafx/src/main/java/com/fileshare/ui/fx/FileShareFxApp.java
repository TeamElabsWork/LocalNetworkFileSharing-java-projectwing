package com.fileshare.ui.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** JavaFX entry point. Builds the main window from the {@code UiServices} given to {@link FxLauncher}. */
public class FileShareFxApp extends Application {

    @Override
    public void start(Stage stage) {
        MainController main = new MainController(FxLauncher.services());
        stage.setTitle("Local Network File Sharing");
        stage.setScene(new Scene(main.createView(), 900, 600));
        stage.show();
    }
}
