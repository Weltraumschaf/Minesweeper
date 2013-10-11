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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

/**
 * Represents the main application code: Play a minesweeper session.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MinesweeperSession {

    /**
     * Log facility.
     */
    private static final Logger LOG = Logger.getLogger(MinesweeperSession.class);
    /**
     * Timer delay.
     */
    private static final int TIMER_DELAY = 500;
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
     * Used to update the stop watch in the status bar.
     */
    private Timer timer;

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
        LOG.debug("Play the session.");
        prepareMainWindow();
        newGame();
    }

    /**
     * Quit the game.
     */
    public void quit() {
        LOG.debug("Quit minesweeper.");
        timer.stop();
    }

    /**
     * Initializes the main windows.
     */
    private void prepareMainWindow() {
        LOG.debug("Prepare main window.");
        mainWindow.setVersionInfoListener(MenuItemListeners.createVersionListener(mainWindow, version));
        mainWindow.setNewGameListener(MenuItemListeners.createNewGameListener(this));
        mainWindow.setQuitListener(MenuItemListeners.createQuitListener(mainWindow));
        mainWindow.setResizeFieldListener(MenuItemListeners.createResizeFieldListener(this, mainWindow));
        mainWindow.init();
        initStatusBar();
        mainWindow.setVisible(true);
    }

    /**
     * Create a new game.
     */
    public void newGame() {
        LOG.debug("Make new game.");
        updateScore();
        currentGame = new Game();
        mainWindow.getStatusbar().setMinesLeft(currentGame.getMineField().countUnflaggedMines());
        mainWindow.repaint();
        currentGame.start();
        mainWindow.setMineField(currentGame.getMineField());
    }

    /**
     * Update the session score.
     */
    private void updateScore() {
        LOG.debug("Update score.");

        if (null != currentGame) {
            if (currentGame.isGameOver()) {
                score.incrementGamesLost();
            } else if (currentGame.hasWon()) {
                score.incrementGamesWon();
            }
        }
    }

    /**
     * Initialize the status bar.
     */
    private void initStatusBar() {
        LOG.debug("Init status bar.");
        score.addObserver(mainWindow.getStatusbar());
        timer = new Timer(TIMER_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                mainWindow.getStatusbar().setElapsedTime(currentGame.getTime());
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();
    }

    /**
     * Get the current game.
     *
     * @return never {@code null}
     */
    public Game getCurrentGame() {
        return currentGame;
    }

}
