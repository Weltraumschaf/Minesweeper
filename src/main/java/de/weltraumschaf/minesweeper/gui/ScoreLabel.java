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
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

/**
 * UI element to display number of games (sum, lost, and won).
 *
 * May observes {@link Score} object.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class ScoreLabel extends JLabel implements Observer {

    /**
     * Logging facility.
     */
    private static final Logger LOG = Logger.getLogger(ScoreLabel.class);
    /**
     * Format of displayed label text.
     */
    private static final String DISPLAY_FORMAT = "Won: %d, Lost: %d";

    /**
     * Count of won games.
     */
    private int won;
    /**
     * Count of lost games.
     */
    private int lost;

    /**
     * Dedicated constructor.
     *
     * Set text with default values.
     */
    public ScoreLabel() {
        super();
        setText(renderText());
    }

    /**
     * Set the number of won games.
     *
     * @param won must not be less than 0
     */
    public void setWon(int won) {
        Validate.isTrue(won >= 0, "Won number must not be less than 0!");
        this.won = won;
        setText(renderText());
    }

    /**
     * Set the number of lost games.
     *
     * @param lost must not be less than 0
     */
    public void setLost(int lost) {
        Validate.isTrue(lost >= 0, "Lost number must not be less than 0!");
        this.lost = lost;
        setText(renderText());
    }

    /**
     * Renders the display text.
     *
     * @return never {@code null} or empty
     */
    private String renderText() {
        return String.format(DISPLAY_FORMAT, won, lost);
    }

    /**
     * Updates {@link #won} and {@link #lost} from a given {@link Score} observable.
     *
     * Ignores observables not of type {@link Score}.
     *
     * @param observable should be of type {@link Score}
     * @param arg always ignored
     */
    @Override
    public void update(final Observable observable, final Object arg) {
        if (!(observable instanceof Score)) {
            return;
        }

        final Score score = (Score) observable;
        LOG.debug(String.format("Update score label with %s", score));
        setWon(score.getGamesWon());
        setLost(score.getGamesLost());
        repaint();
    }

}
