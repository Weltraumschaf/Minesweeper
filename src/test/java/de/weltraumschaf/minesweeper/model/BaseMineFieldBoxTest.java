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
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link BaseMineFieldBox}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class BaseMineFieldBoxTest {

    //CHECKSTYLE:OFF
    @Rule public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final MineField field = new MineField(2, 2);

    @Test
    public void constructThrowsExceptionIfRowIdLesThanZero() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Row id must not be less than 0!");
        new BaseMineFieldBoxStub(-1, 0, field);
    }

    @Test
    public void constructThrowsExceptionIfColumnIdLesThanZero() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Column id must not be less than 0!");
        new BaseMineFieldBoxStub(0, -1, field);
    }

    @Test
    public void constructThrowsExceptionIfFieldIsNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Field must not be null!");
        new BaseMineFieldBoxStub(0, 0, null);
    }

    private static final class BaseMineFieldBoxStub extends BaseMineFieldBox {

        public BaseMineFieldBoxStub(final int rowId, final int columnId, final MineField field) {
            super(rowId, columnId, field);
        }

        @Override
        public boolean isMine() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

}