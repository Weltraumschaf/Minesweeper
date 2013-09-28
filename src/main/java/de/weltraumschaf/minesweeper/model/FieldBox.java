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

import java.util.List;
import java.util.Observer;

/**
 * Interface for mine field boxes.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public interface FieldBox {

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
    void setFlag(final boolean flagged);
    /**
     * Whether a box is open or not.
     *
     * A open box can not be closed or flagged gain. If it is a bomb the game is over.
     *
     * @return {@code true} if opened, else {@code false}
     */
    boolean isOpen();
    /**
     * Set the box opened.
     *
     * Should only perform any action, if not opened yet.
     *
     * @param open {@code true} for open box, else {@code false}
     */
    void setOpened(final boolean open);
    /**
     * Get the field to which the box belongs.
     *
     * @return never {@code null}
     */
    MineField getField();
    /**
     * Get all direct neighbors of a box.
     *
     * @return contains at least 3 and maximum 8 boxes
     */
    List<FieldBox> getNeighbours();
    /**
     * Count the mines in the direct neighborhood.
     *
     * @return number in range [0,8]
     */
    int countMinesInNeighborhood();
    /**
     * Add an observer.
     *
     * @param o must not be {@code null}
     */
    void addObserver(Observer o);
    /**
     * Delete an observer.
     *
     * @param o must not be {@code null}
     */
    void deleteObserver(Observer o);
    /**
     * Notify observers for changes.
     *
     * Dispatches to {@link #notifyObservers(java.lang.Object)} with {@code null} as argument.
     */
    void notifyObservers();
    /**
     * Notify observers for changes.
     *
     * @param arg may be {@code null}
     */
    void notifyObservers(Object arg);
    /**
     * Delete all observers.
     */
    void deleteObservers();
    /**
     * Determine if box state has changed for observers.
     *
     * @return {@code true} if observers should be notified, else {@code false}
     */
    boolean hasChanged();
    /**
     * Count observers.
     *
     * @return not negative
     */
    int countObservers();
    /**
     * Get the x-position in the mine field.
     *
     * @return [0, width - 1]
     */
    int getY();
    /**
     * Get the y-position in the mine field.
     *
     * @return [0, width - 1]
     */
    int getX();

}
