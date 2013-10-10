/*
 *  LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf@googlemail.com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol-free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf@googlemail.com>
 */
package de.weltraumschaf.minesweeper;

import de.weltraumschaf.minesweeper.model.MinesweeperSession;
import de.weltraumschaf.commons.InvokableAdapter;
import de.weltraumschaf.commons.Version;
import de.weltraumschaf.commons.system.NullExiter;
import java.io.IOException;
import java.util.logging.Level;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

/**
 * Main application class.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class App implements Runnable {

    /**
     * Log facility.
     */
    private static final Logger LOG = Logger.getLogger(App.class);
    private static final String JAVAFX_ENVIRONMENT_NAME = "MINESWEEPER_JAVAFX";
    /**
     * Version information.
     */
    private final Version version;
    private final String[] args;

    /**
     * Dedicated constructor.
     *
     * @param args must not be {@code null}
     */
    public App(final String[] args) {
        super();
        this.args = args.clone();
        this.version = new Version("/de/weltraumschaf/minesweeper/version.properties");
    }

    /**
     * Main entry point of VM.
     *
     * @param args cli arguments from VM
     */
    public static void main(final String[] args) {
        new App(args).run();
    }

    @Override
    public void run() {
        try {
            version.load();
        } catch (IOException ex) {
            LOG.error("Can't load version file!", ex);
        }

        if (useJavaFx()) {
            LOG.debug("Create and launch JavaFX application.");
            new FxApp().launch(args);
        } else {
            LOG.debug("Create and invoke Swing application.");
            InvokableAdapter.main(new SwingApp(args, version));
        }
    }

    /**
     * Determine whether to use Java FX or not.
     *
     * @return {@code true} if Java FX should be used, else {@code false}
     */
    private boolean useJavaFx() {
        return "true".equalsIgnoreCase(System.getenv(JAVAFX_ENVIRONMENT_NAME));
    }

}
