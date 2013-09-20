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
import java.util.Observable;
import org.apache.commons.lang3.Validate;

/**
 * Base implementation of a mine field box.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class BaseMineFieldBox extends Observable implements MineFieldBox {

    /**
     * Row in which this box is positioned in the field (x-coordinate).
     */
    private final int x;
    /**
     * Column in which this box is positioned in the field (y-coordinate).
     */
    private final int y;
    /**
     * Mine field to which the box belongs.
     */
    private final MineField field;
    /**
     * List of direct neighbors fields.
     */
    private List<MineFieldBox> neighbours;
    /**
     * Numbers of mines in neighborhood.
     */
    private int minesInNeighbourhoodCount = -1;
    /**
     * Whether the box is opened.
     */
    private boolean opened;
    /**
     * Whether the box is flagged.
     */
    private boolean flagged;

    /**
     * Dedicated constructor.
     *
     * @param rowId must not be less than 0
     * @param columnId must not be less than 0
     * @param field must not be {@code null}
     */
    public BaseMineFieldBox(final int rowId, final int columnId, final MineField field) {
        super();
        Validate.isTrue(rowId >= 0, "Row id must not be less than 0!");
        this.x = rowId;
        Validate.isTrue(columnId >= 0, "Column id must not be less than 0!");
        this.y = columnId;
        Validate.notNull(field, "Field must not be null!");
        this.field = field;
    }

    @Override
    public int countMinesInNeighborhood() {
        if (minesInNeighbourhoodCount == -1) {
            minesInNeighbourhoodCount = 0;

            for (final MineFieldBox box : getNeighbours()) {
                if (box.isMine()) {
                    ++minesInNeighbourhoodCount;
                }
            }
        }

        return minesInNeighbourhoodCount;
    }

    @Override
    public List<MineFieldBox> getNeighbours() {
        if (neighbours == null) {
            neighbours = field.getNeighboursOfBox(x, y);
        }

        return neighbours;
    }

    @Override
    public boolean isOpen() {
        return opened;
    }

    @Override
    public void setOpened(final boolean opened) {
        setChanged();
        this.opened = opened;
        notifyObservers();
    }

    @Override
    public boolean isFlag() {
        return flagged;
    }

    @Override
    public void setFlag(final boolean flagged) {
        setChanged();
        this.flagged = flagged;
        notifyObservers();
    }

    @Override
    public MineField getField() {
        return field;
    }

    @Override
    public String toString() {
        return String.format("[%s,%s]", x, y);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

}
