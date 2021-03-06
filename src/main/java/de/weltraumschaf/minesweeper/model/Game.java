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

import de.weltraumschaf.minesweeper.gui.Size;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.log4j.Logger;

/**
 * Represents a whole game.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Game {

    /**
     * Log facility.
     */
    private static final Logger LOG = Logger.getLogger(Game.class);
    /**
     * Format string for elapsed time.
     */
    private static final String TIME_FORMAT = "mm:ss";

    /**
     * Mine field to play with.
     */
    private MineField mineField;
    /**
     * Watch to measure time for a game.
     */
    private StopWatch watch = new StopWatch();
    /**
     * Indicates if game was started.
     *
     * Started means first click on a field was done.
     */
    private boolean started;
    /**
     * Whether a mine was opened and game is over.
     */
    private boolean gameOver;
    /**
     * Size of game.
     */
    private Size size;

    /**
     * Dedicated constructor.
     *
     * Initializes {@link #mineField} with a default instance.
     */
    public Game() {
        super();
        mineField = new MineField(this);
        size = Size.SMALL;
    }

    /**
     * Changes the size of the mine field.
     *
     * @param s must not be {@code null}
     */
    public void setSize(final Size s) {
        Validate.notNull(s, "Size must not be null!");
        size = s;
        LOG.debug(String.format("Resize game and set new mine field model %dx%d.", s.getWidth(), s.getHeight()));
        mineField = new MineField(s.getWidth(), s.getHeight(), this);
        reset();
    }

    public boolean hasSize(final Size s) {
        return size == s;
    }

    /**
     * Starts the game.
     */
    public void start() {
        LOG.debug("Start game.");

        if (started) {
            LOG.warn("Game already startet.");
            return;
        }

        started = true;
        reset();
        watch.start();
    }

    /**
     * Stop the game.
     */
    public void stop() {
        LOG.debug("Stop game.");

        if (!started) {
            LOG.warn("Game already stopped.");
            return;
        }

        started = false;
        watch.stop();
    }

    /**
     * Whether the game was won.
     *
     * @return {@code true} if all fields are opened or flagged and no mine was opened, else {@code false}
     */
    public boolean hasWon() {
        LOG.debug("Determine if won...");

        if (isGameOver()) {
            LOG.debug("Game is over, not won!");
            return false;
        } else {
            LOG.debug("Game is not over.");
        }

        if (mineField.allBoxesOpenOrFlagged()) {
            LOG.debug("All boxes opened or flagged.");
            return true;
        } else {
            LOG.debug("Not all boxes opened or flagged!");
            return false;
        }
    }

    /**
     * Set the game as lost if a mine was opened.
     */
    public void setGameOver() {
        gameOver = true;

        for (final FieldBox b : mineField.getBoxes().getAll()) {
            b.setOpened(true);
        }
    }

    /**
     * Whether the game was lost.
     *
     * @return {@code true} if a mine was opened, else {@code false}
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Get the elapsed time if game was stopped.
     *
     * Throws exception if invoked before {@link #stop()} called.
     *
     * @return never {@code null}
     */
    public String getTime() {
        return DurationFormatUtils.formatDuration(watch.getTime(), TIME_FORMAT, true);
    }

    /**
     * Initializes the mine field and reset the stop watch.
     */
    private void reset() {
        LOG.debug("Reset game.");
        mineField.initializeFieldWithBoxes();
        watch.reset();
    }

    /**
     * Get field of game.
     *
     * @return never {@code null}
     */
    public MineField getMineField() {
        return mineField;
    }

    /**
     * Set the mine field.
     *
     * @param field must not be {@code null}
     */
    public void setMineField(final MineField field) {
        Validate.notNull(field, "Must not be null!");
        this.mineField = field;
    }

}
