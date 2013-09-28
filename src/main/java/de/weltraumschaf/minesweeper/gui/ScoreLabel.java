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

import javax.swing.JLabel;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class ScoreLabel extends JLabel {

    private int won;
    private int lost;

    public ScoreLabel() {
        super();
        setText(renderText());
    }

    public void setWon(int won) {
        this.won = won;
        setText(renderText());
    }

    public void setLost(int lost) {
        this.lost = lost;
        setText(renderText());
    }

    private String renderText() {
        return String.format("Played: %d (won: %d, lost %d)", won + lost, won, lost);
    }
}
