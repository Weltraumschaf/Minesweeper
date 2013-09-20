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

import java.util.Observer;

/**
 * Interface for mine field boxes.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface MineFieldBox {

    /**
     * Whether the box is a mine or not.
     *
     * @return {@code true} if box is a mine, else {@code false}
     */
    boolean isMine();
    /**
     * Whether the box is flagged.
     *
     * If a box is flagged it can't be opened.
     *
     * @return {@code true} if box is flagged, else {@code false}
     */
    boolean isFlag();
    /**
     * Set the box flagged.
     *
     * If a box is flagged it can't be opened. A opened box can't be flagged.
     *
     * @param flagged {@code true} for flagging, else {@code false}
     */
    void setFlagged(final boolean flagged);

    boolean isOpen();

    void setOpened(final boolean opened);

    MineField getField();

    int countMinesInNeighborhood();

    void addObserver(Observer o);

    void deleteObserver(Observer o);

    void notifyObservers();

    void notifyObservers(Object arg);

    void deleteObservers();

    boolean hasChanged();

    int countObservers();

}
