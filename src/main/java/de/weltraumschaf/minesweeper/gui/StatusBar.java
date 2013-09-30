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
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import org.apache.commons.lang3.Validate;

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
     * Label text to separate the single parts of the status bar.
     */
    private static final String SEPARATOR = " | ";

    /**
     * Displays the score.
     */
    private ScoreLabel scoreLabel = new ScoreLabel();
    /**
     * Displays the elapsed time.
     */
    private LabeledValue elapsedTime = new LabeledValue("Time");
    /**
     * Displays mines not found.
     */
    private LabeledValue minesLeft = new LabeledValue("Mines");

    /**
     * Dedicated constructor.
     *
     * Initializes {@link #elapsedTime} and {@link #minesLeft} with {@code 0}.
     */
    public StatusBar() {
        super(new FlowLayout(FlowLayout.LEFT, 2, 0));
        add(scoreLabel);
        add(new JLabel(SEPARATOR));
        elapsedTime.setValue("0");
        add(elapsedTime);
        add(new JLabel(SEPARATOR));
        minesLeft.setValue("0");
        add(minesLeft);
        setBorder(new BevelBorder(BevelBorder.LOWERED));
    }

    @Override
    public void update(final Observable observable, final Object arg) {
        if (observable instanceof Score) {
            scoreLabel.update(observable, arg);
        }
    }

    /**
     * Updates the mines left.
     *
     * @param count must not be less than 0
     */
    public void setMinesLeft(final int count) {
        Validate.isTrue(count >= 0, "Count must not be less than 0!");
        minesLeft.setValue(String.valueOf(count));
    }

    /**
     * Set the elapsed time.
     *
     * @param time must not be {@code null}
     */
    public void setElapsedTime(final String time) {
        Validate.notNull(time, "Time must not be null!");
        elapsedTime.setValue(time);
    }

}
