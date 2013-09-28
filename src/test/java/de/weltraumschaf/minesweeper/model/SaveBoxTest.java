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

import java.util.Collections;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link SaveBox}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SaveBoxTest {

    private final MineField field = mock(MineField.class);
    private final SaveBox sut = new SaveBox(1, 2, field);

    @Test
    public void isMine() {
        assertThat(sut.isMine(), is(false));
    }

    @Test
    public void testToString() {
        when(field.getNeighboursOfBox(1, 2)).thenReturn(Collections.<FieldBox>emptyList());
        assertThat(sut.toString(), is(equalTo("0 [1,2] CLOSE")));
    }

}
