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
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class Score extends Observable {

    private int gamesWon;
    private int gamesLost;

    public void incGamesWon() {
        setChanged();
        ++gamesWon;
        notifyObservers();
    }

    public void incGamesLost() {
        setChanged();
        ++gamesLost;
        notifyObservers();
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }

}
