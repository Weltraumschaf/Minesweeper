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

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class MineBox extends BaseMineFieldBox {
    /**
     * http://www.fileformat.info/info/unicode/char/1f4a3/index.htm
     */
    public static final String BOMB = "\uD83D\uDCA3";

    public MineBox(final int rowId, final int columnId, final MineField field) {
        super(rowId, columnId, field);
    }

    @Override
    public boolean isMine() {
        return true;
    }

    @Override
    public String toString() {
        return BOMB + " " + super.toString();
    }

}