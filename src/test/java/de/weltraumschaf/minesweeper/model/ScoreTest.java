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

import java.util.Observer;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link Score}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class ScoreTest {

    private final Score sut = new Score();
    private final Observer observer = mock(Observer.class);

    @Test
    public void incrementGamesWon_notifyObservers() {
        sut.addObserver(observer);
        sut.incrementGamesWon();
        verify(observer, times(1)).update(sut, null);
    }

    @Test
    public void incrementGamesWon() {
        assertThat(sut.getGamesWon(), is(0));
        assertThat(sut.getGamesLost(), is(0));
        sut.incrementGamesWon();
        assertThat(sut.getGamesWon(), is(1));
        assertThat(sut.getGamesLost(), is(0));
        sut.incrementGamesWon();
        assertThat(sut.getGamesWon(), is(2));
        assertThat(sut.getGamesLost(), is(0));
    }

    @Test
    public void incrementGamesLost_notifyObservers() {
        sut.addObserver(observer);
        sut.incrementGamesLost();
        verify(observer, times(1)).update(sut, null);
    }

    @Test
    public void incrementGamesLost() {
        assertThat(sut.getGamesWon(), is(0));
        assertThat(sut.getGamesLost(), is(0));
        sut.incrementGamesLost();
        assertThat(sut.getGamesWon(), is(0));
        assertThat(sut.getGamesLost(), is(1));
        sut.incrementGamesLost();
        assertThat(sut.getGamesWon(), is(0));
        assertThat(sut.getGamesLost(), is(2));
    }

}
