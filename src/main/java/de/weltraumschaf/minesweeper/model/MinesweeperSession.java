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

package de.weltraumschaf.minesweeper.model;

import de.weltraumschaf.commons.Version;
import de.weltraumschaf.minesweeper.control.MenuItemListeners;
import de.weltraumschaf.minesweeper.gui.MainWindow;
import org.apache.commons.lang3.Validate;

/**
 * Represents the main application code: Play a minesweeper session.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MinesweeperSession {

    /**
     * Version information.
     */
    private final Version version;
    /**
     * Score of the current played session.
     */
    private final Score score = new Score();
    /**
     * The games main windows.
     */
    private final MainWindow mainWindow = new MainWindow("Minesweeper");
    /**
     * Current played game.
     */
    private Game currentGame;

    /**
     * Dedicated constructor.
     *
     * @param version must not be {@code null}
     */
    public MinesweeperSession(final Version version) {
        super();
        Validate.notNull(version, "Version must not be null!");
        this.version = version;
    }

    /**
     * Play minesweeper.
     */
    public void play() {
        initMainWindow();
        newGame();
    }

    /**
     * Initializes the main windows.
     */
    private void initMainWindow() {
        mainWindow.setVersionInfoListener(MenuItemListeners.createVersionListener(mainWindow, version));
        mainWindow.setNewGameListener(MenuItemListeners.createNewGameListener(mainWindow, this));
        mainWindow.setQuitListener(MenuItemListeners.createQuitListener(mainWindow));
        mainWindow.init();
        mainWindow.setVisible(true);
    }

    /**
     * Create a new game.
     */
    public void  newGame() {
        currentGame = new Game();
        currentGame.start(0, 0);
        mainWindow.setMineField(currentGame.getMineField());
        mainWindow.repaint();
    }

}
