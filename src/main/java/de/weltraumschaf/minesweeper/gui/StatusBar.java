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

package de.weltraumschaf.minesweeper.gui;

import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 * UI element for status bar of game.
 *
 * Shows:
 * <ul>
 * <li>time elapsed</li>
 * <li>games played, won, lost</li>
 * <li>Mines not found yet</li>
 * </ul>
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class StatusBar extends JPanel implements Observer {
    /**
     * Number of rows of used grid layout.
     */
    private static final int ROWS = 1;
    /**
     * Number of columns of used grid layout.
     */
    private static final int COLUMS = 3;
    /**
     * Displays the score.
     */
    private ScoreLabel score = new ScoreLabel();
    /**
     * Displays the elapsed time.
     */
    private LabeledValue elapsedTime = new LabeledValue("Time:");
    /**
     * Displays mines not found.
     */
    private LabeledValue minesLeft = new LabeledValue("Mines:");

    /**
     * Dedicated constructor.
     *
     * Initializes {@link #elapsedTime} and {@link #minesLeft} with {@code 0}.
     */
    public StatusBar() {
        super(new GridLayout(ROWS, COLUMS));
        add(score);
        elapsedTime.setValue("0");
        add(elapsedTime);
        minesLeft.setValue("0");
        add(minesLeft);
    }

    @Override
    public void update(final Observable observable, final Object arg) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

}
