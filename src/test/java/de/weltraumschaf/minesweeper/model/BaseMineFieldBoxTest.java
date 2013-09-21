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

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link BaseMineFieldBox}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class BaseMineFieldBoxTest {

    //CHECKSTYLE:OFF
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    /**
     * Count of neighbors.
     */
    private static final int NEIGHBOURCOUNT = 8;
    /**
     * Dependency for SUT.
     */
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

    private List<FieldBox> createNeighbours() {
        final List<FieldBox> neighbors = new ArrayList<FieldBox>(NEIGHBOURCOUNT);

        for (int i = 0; i < NEIGHBOURCOUNT; ++i) {
            neighbors.add(new SaveBox(0, 0, field));
        }

        return neighbors;
    }

    @Test
    public void countMinesInNeighborhood_noMines() {
        final BaseMineFieldBoxStubFixedNeighbors sut = new BaseMineFieldBoxStubFixedNeighbors(0, 0, field);
        final List<FieldBox> neighbors = createNeighbours();
        sut.setNeighbors(neighbors);
        assertThat(sut.countMinesInNeighborhood(), is(0));
    }

    @Test
    public void countMinesInNeighborhood_oneMines() {
        final BaseMineFieldBoxStubFixedNeighbors sut = new BaseMineFieldBoxStubFixedNeighbors(0, 0, field);
        final List<FieldBox> neighbors = createNeighbours();
        neighbors.set(3, new MineBox(0, 0, field));
        sut.setNeighbors(neighbors);
        assertThat(sut.countMinesInNeighborhood(), is(1));
    }

    @Test
    public void countMinesInNeighborhood_threeMines() {
        final BaseMineFieldBoxStubFixedNeighbors sut = new BaseMineFieldBoxStubFixedNeighbors(0, 0, field);
        final List<FieldBox> neighbors = createNeighbours();
        neighbors.set(3, new MineBox(0, 0, field));
        neighbors.set(5, new MineBox(0, 0, field));
        neighbors.set(7, new MineBox(0, 0, field));
        sut.setNeighbors(neighbors);
        assertThat(sut.countMinesInNeighborhood(), is(3));
    }

    @Test
    public void countMinesInNeighborhood_allMines() {
        final BaseMineFieldBoxStubFixedNeighbors sut = new BaseMineFieldBoxStubFixedNeighbors(0, 0, field);
        final List<FieldBox> neighbors = new ArrayList<FieldBox>(NEIGHBOURCOUNT);

        for (int i = 0; i < NEIGHBOURCOUNT; ++i) {
            neighbors.add(new MineBox(0, 0, field));
        }

        sut.setNeighbors(neighbors);
        assertThat(sut.countMinesInNeighborhood(), is(NEIGHBOURCOUNT));
    }

    @Test
    public void setOpened_triggersObserver() {
        final BaseMineFieldBox sut = new BaseMineFieldBoxStub(0, 0, field);
        final Observer observer = mock(Observer.class);
        sut.addObserver(observer);
        assertThat(sut.isOpen(), is(false));
        sut.setOpened();
        assertThat(sut.isOpen(), is(true));
        verify(observer, times(1)).update(sut, null);
    }

    @Test
    public void setFlagged_triggersObserver() {
        final BaseMineFieldBox sut = new BaseMineFieldBoxStub(0, 0, field);
        final Observer observer = mock(Observer.class);
        sut.addObserver(observer);
        assertThat(sut.isFlag(), is(false));
        sut.setFlag(true);
        assertThat(sut.isFlag(), is(true));
        verify(observer, times(1)).update(sut, null);
    }

    @Test
    public void getField() {
        final BaseMineFieldBox sut = new BaseMineFieldBoxStub(0, 0, field);
        assertThat(sut.getField(), is(sameInstance(field)));
    }

    private static class BaseMineFieldBoxStub extends BaseMineFieldBox {

        public BaseMineFieldBoxStub(final int rowId, final int columnId, final MineField field) {
            super(rowId, columnId, field);
        }

        @Override
        public boolean isMine() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private static class BaseMineFieldBoxStubFixedNeighbors extends BaseMineFieldBoxStub {

        private List<FieldBox> neighbors;

        public BaseMineFieldBoxStubFixedNeighbors(final int rowId, final int columnId, final MineField field) {
            super(rowId, columnId, field);
        }

        public void setNeighbors(List<FieldBox> neighbors) {
            this.neighbors = neighbors;
        }

        @Override
        public List<FieldBox> getNeighbours() {
            return neighbors;
        }

    }
}
