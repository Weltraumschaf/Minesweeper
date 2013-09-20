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

    /**
     * Version information.
     */
    private final Version version;

    /**
     * Dedicated constructor.
     *
     * @param args must not be {@code null}
     */
    public App(final String[] args) {
        super(args);
        version = new Version("/de/weltraumschaf/minesweeper/version.properties");
    }

    /**
     * Main entry point of VM.
     *
     * @param args cli arguments from VM
     */
    public static void main(final String[] args) {
        InvokableAdapter.main(new App(args));
    }

    @Override
    public void run() {
        final MineField mineField;

        if (getArgs().length == 2) {
            mineField = new MineField(Integer.parseInt(getArgs()[0]), Integer.parseInt(getArgs()[1]));
        } else {
            mineField = new MineField();
        }

        mineField.initializeFieldWithBoxes();
        final MainWindow mainWindow = new MainWindow("Minesweeper", mineField);
        mainWindow.init();
        mainWindow.setVisible(true);
    }

    @Override
    public void execute() throws Exception {
        setExiter(new NullExiter());
//        version.load();
        SwingUtilities.invokeLater(this);
    }
}
