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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link MineField}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MineFieldTest {

    //CHECKSTYLE:OFF
    @Rule public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON

    @Test
    public void throwsExceptionIfConstructWithHeightLessThanOne() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Height must not be less than 1!");
        new MineField(0, 1);
    }

    @Test
    public void throwsExceptionIfConstructWithWidthLessThanOne() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Width must not be less than 1!");
        new MineField(1, 0);
    }

}