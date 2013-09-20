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

import org.apache.commons.lang3.time.StopWatch;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Game {

    private MineField field = new MineField();
    private StopWatch watch = new StopWatch();
    private boolean started;

    public void resize(final int width, final int height) {
        field = new MineField(height, width);
    }

    /**
     * Starts the game.
     *
     * @param x coordinate of first clocked box
     * @param y coordinate of first clocked box
     */
    public void start(final int x, final int y) {
        if (started) {
            throw new IllegalStateException("Game already started!");
        }

        started = true;
        reset(x, y);
        watch.start();
    }

    public void stop() {
        if (!started) {
            throw new IllegalStateException("Game already stopped!");
        }

        started = false;
        watch.stop();
    }

    public boolean hasWon() {
        return field.hasWon();
    }

    public boolean isGameOver() {
        return field.isGameOver();
    }

    public String getTime() {
        if (started) {
            throw new IllegalStateException("Game is running! Call stop() first.");
        }

        return watch.toString();
    }

    /**
     *
     * @param x coordinate of first clocked box
     * @param y coordinate of first clocked box
     */
    private void reset(final int x, final int y) {
        field.initializeFieldWithBoxes(x, y);
        watch.reset();
    }

}
