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

package de.weltraumschaf.minesweeper.gui;

import de.weltraumschaf.minesweeper.model.FieldBox;
import de.weltraumschaf.minesweeper.model.Game;
import de.weltraumschaf.minesweeper.model.MineField;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link FieldBoxButton}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class FieldBoxButtonTest {

    //CHECKSTYLE:OFF
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final FieldBoxButton sut = spy(new FieldBoxButton());
    private final FieldBox box = mock(FieldBox.class);
    private final Game game = mock(Game.class);
    private final MineField field = spy(new MineField(game));

    @Test
    public void setState_throwsExceptionIfNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("State must not be null!");
        sut.setState(null);
    }

    @Test
    public void setBox_throwsExceptionIfNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Box must not be null!");
        sut.setBox(null);
    }

    @Test
    public void setAndGetBox() {
        assertThat(sut.getBox(), is(nullValue()));
        sut.setBox(box);
        assertThat(sut.getBox(), is(sameInstance(box)));
    }

    @Test
    public void open_throwsExceptionIfBoxNotSet() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Box model is null! Set box first.");
        sut.open();
    }

    @Test
    public void open_isMineAndGameOverAndIsFlagged() {
        when(game.isGameOver()).thenReturn(Boolean.TRUE);
        when(box.getField()).thenReturn(field);
        when(box.isFlag()).thenReturn(Boolean.TRUE);
        when(box.isMine()).thenReturn(Boolean.TRUE);
        sut.setBox(box);
        assertThat(sut.isInState(FieldBoxButton.State.OPEN), is(false));
        sut.open();
        assertThat(sut.getIcon(), is(sameInstance(ImageIcons.BOMB.getResource())));
        assertThat(sut.isInState(FieldBoxButton.State.OPEN), is(true));
        verify(sut, atLeastOnce()).repaint();
    }

    @Test
    public void open_isMineAndGameOverAndIsNotFlagged() {
        when(game.isGameOver()).thenReturn(Boolean.TRUE);
        when(box.getField()).thenReturn(field);
        when(box.isFlag()).thenReturn(Boolean.FALSE);
        when(box.isMine()).thenReturn(Boolean.TRUE);
        sut.setBox(box);
        assertThat(sut.isInState(FieldBoxButton.State.OPEN), is(false));
        sut.open();
        assertThat(sut.getIcon(), is(sameInstance(ImageIcons.BOMB_EXPLODED.getResource())));
        assertThat(sut.isInState(FieldBoxButton.State.OPEN), is(true));
        verify(sut, atLeastOnce()).repaint();
    }

    @Test
    public void open_isMineAndGameNotOver() {
        when(game.isGameOver()).thenReturn(Boolean.FALSE);
        when(box.getField()).thenReturn(field);
        when(box.isMine()).thenReturn(Boolean.TRUE);
        sut.setBox(box);
        assertThat(sut.isInState(FieldBoxButton.State.OPEN), is(false));
        sut.open();
        assertThat(sut.getIcon(), is(sameInstance(ImageIcons.BOMB_EXPLODED.getResource())));
        assertThat(sut.isInState(FieldBoxButton.State.OPEN), is(true));
        verify(sut, atLeastOnce()).repaint();
        verify(game, times(1)).setGameOver();
    }

    @Test
    public void open_isNotMineAndMoreThanFifveNeighbors() {
        when(box.getField()).thenReturn(field);
        when(box.isMine()).thenReturn(Boolean.FALSE);
        when(box.countMinesInNeighborhood()).thenReturn(5);
        sut.setBox(box);
        assertThat(sut.isInState(FieldBoxButton.State.OPEN), is(false));
        sut.open();
        assertThat(sut.getIcon(), is(sameInstance(ImageIcons.FIVE_NEIGHBOR.getResource())));
        assertThat(sut.isInState(FieldBoxButton.State.OPEN), is(true));
        verify(sut, atLeastOnce()).repaint();
    }

    @Test
    public void open_isNotMineAndMoreThanZeroNeighbors() {
        when(box.getField()).thenReturn(field);
        when(box.isMine()).thenReturn(Boolean.FALSE);
        when(box.countMinesInNeighborhood()).thenReturn(0);
        final List<FieldBox> neighbors = Arrays.asList(
            mock(FieldBox.class), mock(FieldBox.class), mock(FieldBox.class)
        );
        when(box.getNeighbours()).thenReturn(neighbors);
        sut.setBox(box);
        assertThat(sut.isInState(FieldBoxButton.State.OPEN), is(false));
        sut.open();
        assertThat(sut.getIcon(), is(sameInstance(ImageIcons.BLANK.getResource())));
        assertThat(sut.isInState(FieldBoxButton.State.OPEN), is(true));
        verify(sut, atLeastOnce()).repaint();

        for (final FieldBox neighbor : neighbors) {
            verify(neighbor, times(1)).setOpened(true);
        }
    }

    @Test
    public void close_throwsExceptionIfBoxNotSet() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Box model is null! Set box first.");
        sut.setState(FieldBoxButton.State.OPEN);
        sut.close();
    }

    @Test
    public void close() {
        sut.setBox(box);
        sut.setState(FieldBoxButton.State.FLAG);
        assertThat(sut.isInState(FieldBoxButton.State.CLOSED), is(false));
        sut.close();
        assertThat(sut.isInState(FieldBoxButton.State.CLOSED), is(true));
        assertThat(sut.getIcon(), is(sameInstance(ImageIcons.CLOSED.getResource())));
        verify(box, times(1)).setFlag(false);
        verify(sut, atLeastOnce()).repaint();
    }

    @Test
    public void flag_throwsExceptionIfBoxNotSet() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Box model is null! Set box first.");
        sut.flag();
    }

    @Test
    public void flag() {
        sut.setBox(box);
        sut.setState(FieldBoxButton.State.CLOSED);
        assertThat(sut.isInState(FieldBoxButton.State.FLAG), is(false));
        sut.flag();
        assertThat(sut.isInState(FieldBoxButton.State.FLAG), is(true));
        assertThat(sut.getIcon(), is(sameInstance(ImageIcons.FLAG.getResource())));
        verify(box, times(1)).setFlag(true);
        verify(sut, atLeastOnce()).repaint();
    }

    @Test
    public void determineIcon_throwExceptionIfBoxIsNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Box model is null! Set box first.");
        sut.determineIcon();
    }

    @Test
    public void determineIcon_isMine() {
        when(box.isMine()).thenReturn(Boolean.TRUE);
        sut.setBox(box);
        assertThat(sut.determineIcon(), is(sameInstance(ImageIcons.BOMB.getResource())));
    }

    @Test
    public void determineIcon_isNotMine() {
        when(box.isMine()).thenReturn(Boolean.FALSE);
        when(box.countMinesInNeighborhood()).thenReturn(1);
        sut.setBox(box);
        assertThat(sut.determineIcon(), is(sameInstance(ImageIcons.ONE_NEIGHBOR.getResource())));
    }

    @Test
    public void determineNumberIcon_zeroNeighbotrs() {
        when(box.countMinesInNeighborhood()).thenReturn(0);
        sut.setBox(box);
        assertThat(sut.determineNumberIcon(), is(sameInstance(ImageIcons.BLANK.getResource())));
    }

    @Test
    public void determineNumberIcon_oneNeighbotrs() {
        when(box.countMinesInNeighborhood()).thenReturn(1);
        sut.setBox(box);
        assertThat(sut.determineNumberIcon(), is(sameInstance(ImageIcons.ONE_NEIGHBOR.getResource())));
    }

    @Test
    public void determineNumberIcon_twoNeighbotrs() {
        when(box.countMinesInNeighborhood()).thenReturn(2);
        sut.setBox(box);
        assertThat(sut.determineNumberIcon(), is(sameInstance(ImageIcons.TWO_NEIGHBOR.getResource())));
    }

    @Test
    public void determineNumberIcon_threeNeighbotrs() {
        when(box.countMinesInNeighborhood()).thenReturn(3);
        sut.setBox(box);
        assertThat(sut.determineNumberIcon(), is(sameInstance(ImageIcons.THREE_NEIGHBOR.getResource())));
    }

    @Test
    public void determineNumberIcon_fourNeighbotrs() {
        when(box.countMinesInNeighborhood()).thenReturn(4);
        sut.setBox(box);
        assertThat(sut.determineNumberIcon(), is(sameInstance(ImageIcons.FOUR_NEIGHBOR.getResource())));
    }

    @Test
    public void determineNumberIcon_fiveNeighbotrs() {
        when(box.countMinesInNeighborhood()).thenReturn(5);
        sut.setBox(box);
        assertThat(sut.determineNumberIcon(), is(sameInstance(ImageIcons.FIVE_NEIGHBOR.getResource())));
    }

    @Test
    public void determineNumberIcon_sixNeighbotrs() {
        when(box.countMinesInNeighborhood()).thenReturn(6);
        sut.setBox(box);
        assertThat(sut.determineNumberIcon(), is(sameInstance(ImageIcons.SIX_NEIGHBOR.getResource())));
    }

    @Test
    public void determineNumberIcon_sevenNeighbotrs() {
        when(box.countMinesInNeighborhood()).thenReturn(7);
        sut.setBox(box);
        assertThat(sut.determineNumberIcon(), is(sameInstance(ImageIcons.SEVEN_NEIGHBOR.getResource())));
    }

    @Test
    public void determineNumberIcon_eightNeighbotrs() {
        when(box.countMinesInNeighborhood()).thenReturn(8);
        sut.setBox(box);
        assertThat(sut.determineNumberIcon(), is(sameInstance(ImageIcons.EIGHT_NEIGHBOR.getResource())));
    }

    @Test
    public void determineNumberIcon_throwExceptionIfMinusOne() {
        when(box.countMinesInNeighborhood()).thenReturn(-1);
        sut.setBox(box);
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Unsupported neighbor count: -1!");
        sut.determineNumberIcon();
    }

    @Test
    public void determineNumberIcon_throwExceptionIfNine() {
        when(box.countMinesInNeighborhood()).thenReturn(9);
        sut.setBox(box);
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Unsupported neighbor count: 9!");
        sut.determineNumberIcon();
    }


}
