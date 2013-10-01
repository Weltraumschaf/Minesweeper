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
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link Game}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class GameTest {

    private final Game sut = new Game();
    private MineField field;

    @Before
    public void setUp() {
        field = new MineField(2, 2, sut);
        sut.setMineField(field);
    }

    @Test
    public void setGameOver() {
        field.setInitialized(true);
        final SaveBox saveBox1 = spy(new SaveBox(0, 0, field));
        field.setBox(0, 0, saveBox1);
        final SaveBox saveBox2 = spy(new SaveBox(0, 1, field));
        field.setBox(1, 0, saveBox2);
        final SaveBox saveBox3 = spy(new SaveBox(1, 0, field));
        field.setBox(0, 1, saveBox3);
        final SaveBox saveBox4 = spy(new SaveBox(1, 1, field));
        field.setBox(1, 1, saveBox4);

        assertThat(sut.isGameOver(), is(false));
        sut.setGameOver();
        assertThat(sut.isGameOver(), is(true));

        verify(saveBox1, times(1)).setOpened(true);
        verify(saveBox2, times(1)).setOpened(true);
        verify(saveBox3, times(1)).setOpened(true);
        verify(saveBox4, times(1)).setOpened(true);
    }

    @Test
    public void hasWon_gameOver() {
        field.setInitialized(true);
        final SaveBox saveBox1 = new SaveBox(0, 0, field);
        field.setBox(0, 0, saveBox1);
        final SaveBox saveBox2 = new SaveBox(0, 1, field);
        field.setBox(1, 0, saveBox2);
        final SaveBox saveBox3 = new SaveBox(1, 0, field);
        field.setBox(0, 1, saveBox3);
        final SaveBox saveBox4 = new SaveBox(1, 1, field);
        field.setBox(1, 1, saveBox4);

        assertThat(sut.hasWon(), is(false));
        assertThat(sut.isGameOver(), is(false));
        sut.setGameOver();
        assertThat(sut.hasWon(), is(false));
        assertThat(sut.isGameOver(), is(true));
    }

    @Test
    public void hasWon_notGameOverAllClosed() {
        field.setInitialized(true);
        final SaveBox saveBox1 = new SaveBox(0, 0, field);
        field.setBox(0, 0, saveBox1);
        final SaveBox saveBox2 = new SaveBox(0, 1, field);
        field.setBox(1, 0, saveBox2);
        final SaveBox saveBox3 = new SaveBox(1, 0, field);
        field.setBox(0, 1, saveBox3);
        final SaveBox saveBox4 = new SaveBox(1, 1, field);
        field.setBox(1, 1, saveBox4);

        assertThat(sut.hasWon(), is(false));
        assertThat(sut.isGameOver(), is(false));
    }

    @Test
    public void hasWon_notGameOverAllOpen() {
        field.setInitialized(true);
        final SaveBox saveBox1 = new SaveBox(0, 0, field);
        saveBox1.setOpened(true);
        field.setBox(0, 0, saveBox1);
        final SaveBox saveBox2 = new SaveBox(0, 1, field);
        saveBox2.setOpened(true);
        field.setBox(1, 0, saveBox2);
        final SaveBox saveBox3 = new SaveBox(1, 0, field);
        saveBox3.setOpened(true);
        field.setBox(0, 1, saveBox3);
        final SaveBox saveBox4 = new SaveBox(1, 1, field);
        saveBox4.setOpened(true);
        field.setBox(1, 1, saveBox4);

        assertThat(sut.hasWon(), is(true));
        assertThat(sut.isGameOver(), is(false));
    }

}