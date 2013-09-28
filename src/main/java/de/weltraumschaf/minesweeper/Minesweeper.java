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

import de.weltraumschaf.commons.Version;
import de.weltraumschaf.minesweeper.control.MenuItemListeners;
import de.weltraumschaf.minesweeper.gui.MainWindow;
import de.weltraumschaf.minesweeper.model.MineField;
import de.weltraumschaf.minesweeper.model.Score;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class Minesweeper {

    /**
     * Version information.
     */
    private final Version version;

    private final Score score = new Score();
    private final MainWindow mainWindow = new MainWindow("Minesweeper");

    public Minesweeper(Version version) {
        super();
        this.version = version;
    }

    public void play() {
        initMainWindow();
        final MineField mineField = new MineField();
        mineField.initializeFieldWithBoxes(0, 0);
        mainWindow.setMineField(mineField);
        mainWindow.repaint();
    }

    private void initMainWindow() {
        mainWindow.setVersionInfoListener(MenuItemListeners.createVersionListener(mainWindow, version));
        mainWindow.setNewGameListener(MenuItemListeners.createNewGameListener(mainWindow));
        mainWindow.setQuitListener(MenuItemListeners.createQuitListener(mainWindow));
        mainWindow.init();
        mainWindow.setVisible(true);
    }
}
