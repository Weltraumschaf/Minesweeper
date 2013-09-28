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

import de.weltraumschaf.minesweeper.model.Score;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 * Status bar of game.
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
    private static final int ROWS = 1;
    private static final int COLS = 3;

    private ScoreLabel score = new ScoreLabel();
    private LabeledValue timeElapsed = new LabeledValue("Time:");
    private LabeledValue minesLeft = new LabeledValue("Mines:");

    public StatusBar() {
        super(new GridLayout(ROWS, COLS, 1, 0));
        add(score);
        timeElapsed.setValue("0");
        add(timeElapsed);
        minesLeft.setValue("0");
        add(minesLeft);
    }

    @Override
    public void update(final Observable observable, final Object arg) {
     
    }

}
