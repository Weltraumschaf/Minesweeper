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

import java.util.Observable;

/**
 * Counts the number of won/lost gams of a session.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Score extends Observable {

    /**
     * Number of games won.
     */
    private int gamesWon;
    /**
     * Number of games lost.
     */
    private int gamesLost;

    /**
     * Increments count of won games.
     *
     * Also notify all observers.
     */
    public void incrementGamesWon() {
        setChanged();
        ++gamesWon;
        notifyObservers();
    }

    /**
     * Increments count of lost games.
     *
     * Also notify all observers.
     */
    public void incrementGamesLost() {
        setChanged();
        ++gamesLost;
        notifyObservers();
    }

    /**
     * Get count of won games.
     *
     * @return not negative number
     */
    public int getGamesWon() {
        return gamesWon;
    }

    /**
     * Get count of lost games.
     *
     * @return not negative number
     */
    public int getGamesLost() {
        return gamesLost;
    }

}
