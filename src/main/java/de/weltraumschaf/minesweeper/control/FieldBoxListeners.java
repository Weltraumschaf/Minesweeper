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

package de.weltraumschaf.minesweeper.control;

import java.awt.event.MouseAdapter;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class FieldBoxListeners {

    private FieldBoxListeners() {
        super();
    }

    public static MouseAdapter createClickListener() {
        return new FieldBoxButtonListener();
    }
}
