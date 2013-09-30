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

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.StopWatch;

/**
 * Represents a whole game.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Game {

    /**
     * Mine field to play with.
     */
    private MineField field = new MineField();
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
     * Changes the size of the mine field.
     *
     * @param width must not be less than 1
     * @param height must not be less than 1
     */
    public void resize(final int width, final int height) {
        field = new MineField(height, width);
    }

    /**
     * Starts the game.
     */
    public void start() {
        if (started) {
            throw new IllegalStateException("Game already started!");
        }

        started = true;
        reset();
        watch.start();
    }

    /**
     * Stop the game.
     */
    public void stop() {
        if (!started) {
            throw new IllegalStateException("Game already stopped!");
        }

        started = false;
        watch.stop();
    }

    /**
     * whether the game was won.
     *
     * @return {@code true} if game was won, else {@code false}
     */
    public boolean hasWon() {
        return field.hasWon();
    }

    /**
     * Whether the game is over.
     *
     * @return {@code true} if mine was opened, else {@code false}
     */
    public boolean isGameOver() {
        return field.isGameOver();
    }

    /**
     * Get the elapsed time if game was stopped.
     *
     * Throws exception if invoked before {@link #stop()} called.
     *
     * @return never {@code null}
     */
    public String getTime() {
        return DurationFormatUtils.formatDuration(watch.getTime(), "HH:mm:ss", true);
    }

    /**
     * Initializes the mine field and reset the stop watch.
     */
    private void reset() {
        field.initializeFieldWithBoxes();
        watch.reset();
    }

    /**
     * Get field of game.
     *
     * @return never {@code null}
     */
    public MineField getMineField() {
        return field;
    }

}
