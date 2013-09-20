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

import de.weltraumschaf.minesweeper.model.MineField;
import de.weltraumschaf.minesweeper.gui.MainWindow;
import de.weltraumschaf.commons.InvokableAdapter;
import de.weltraumschaf.commons.Version;
import de.weltraumschaf.commons.system.NullExiter;
import javax.swing.SwingUtilities;

/**
 * Main application class.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class App extends InvokableAdapter implements Runnable {

    private static final int DEFAULT_WIDTH = 8;
    private static final int DEFAULT_HEIGHT = 8;

    private final MineField mineField;
    /**
     * Version information.
     */
    private final Version version;

    public static void main(final String[] args) {
        InvokableAdapter.main(new App(args));
    }

    public App(final String[] args) {
        super(args);
        final int width;
        final int height;

        if (args.length == 2) {
            width = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[1]);
        } else {
            width = DEFAULT_WIDTH;
            height = DEFAULT_HEIGHT;
        }

        this.mineField = new MineField(width, height);
        this.version = new Version("/de/weltraumschaf/minesweeper/version.properties");
    }

    @Override
    public void run() {
        mineField.initializeFieldWithBoxes();
        final MainWindow mainWindow = new MainWindow("Minesweeper", mineField);
        mainWindow.init();
        mainWindow.setVisible(true);
    }

    @Override
    public void execute() throws Exception {
        setExiter(new NullExiter());
        SwingUtilities.invokeLater(this);
    }
}
