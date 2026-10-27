package com.fileshare.app;

import com.fileshare.ui.cli.CliRepl;
import com.fileshare.ui.fx.FxLauncher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;

/**
 * Application entry point.
 * <pre>
 *   (no args)        interactive CLI demo
 *   --gui            JavaFX window
 *   --config &lt;dir&gt;   directory containing pipeline.properties (default: ./config)
 * </pre>
 */
public final class Main {
    private Main() {}

    public static void main(String[] args) throws Exception {
        boolean gui = false;
        Path configDir = Path.of("config");
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--gui" -> gui = true;
                case "--config" -> configDir = Path.of(args[++i]);
                default -> {
                    System.err.println("Unknown argument: " + args[i]);
                    System.exit(2);
                }
            }
        }
        try (AppContext context = PipelineFactory.create(configDir)) {
            context.start();
            if (gui) {
                FxLauncher.launch(context.uiServices());
            } else {
                new CliRepl(context.uiServices(), new BufferedReader(new InputStreamReader(System.in)), System.out).run();
            }
        }
    }
}
