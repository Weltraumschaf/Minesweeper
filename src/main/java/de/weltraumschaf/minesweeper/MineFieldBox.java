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

package de.weltraumschaf.minesweeper;

import java.util.List;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
abstract class MineFieldBox {

    private final int rowId;
    private final int columnId;
    private final MineField field;
    private List<MineFieldBox> neighbours;
    private int minesInNeighbourhoodCount = -1;
    private boolean opened;
    private boolean flagged;

    public MineFieldBox(final int rowId, final int columnId, final MineField field) {
        super();
        this.rowId = rowId;
        this.columnId = columnId;
        this.field = field;
    }

    public abstract boolean isMine();

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

    protected List<MineFieldBox> getNeighbours() {
        if (neighbours == null) {
            neighbours = field.getNeighboursOfBox(rowId, columnId);
        }

        return neighbours;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(final boolean opened) {
        this.opened = opened;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(final boolean flagged) {
        this.flagged = flagged;
    }

    MineField getField() {
        return field;
    }

    @Override
    public String toString() {
        return "(" + rowId + ", " + columnId + ')';
    }

}
