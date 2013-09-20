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

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link MineBox}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MineBoxTest {

    private final MineField field = new MineField(2, 2);
    private final MineBox sut = new MineBox(1, 2, field);
    @Test
    public void isMine() {
        assertThat(sut.isMine(), is(true));
    }

    @Test
    public void testToString() {
        assertThat(sut.toString(), is(equalTo("")));
    }

}
