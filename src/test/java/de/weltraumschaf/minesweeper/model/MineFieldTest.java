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
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link MineField}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MineFieldTest {

    //CHECKSTYLE:OFF
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final MineField sut = new MineField();

    @Before
    public void initializeFieldWithBoxes() {
        sut.initializeFieldWithBoxes();
    }

    private FieldBox createClosedBox() {
        final FieldBox box = mock(FieldBox.class);
        when(box.isOpen()).thenReturn(Boolean.FALSE);
        when(box.isFlag()).thenReturn(Boolean.FALSE);
        return box;
    }

    private FieldBox createFlagBox() {
        final FieldBox box = mock(FieldBox.class);
        when(box.isOpen()).thenReturn(Boolean.FALSE);
        when(box.isFlag()).thenReturn(Boolean.TRUE);
        return box;
    }

    private FieldBox createOpenBox() {
        final FieldBox box = mock(FieldBox.class);
        when(box.isOpen()).thenReturn(Boolean.TRUE);
        when(box.isFlag()).thenReturn(Boolean.FALSE);
        return box;
    }

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

    @Test
    public void getNeighboursOfBox_atCornerReturnsThree() {
        assertThat(sut.getNeighboursOfBox(0, 0), hasSize(3));
        assertThat(sut.getNeighboursOfBox(0, 7), hasSize(3));
        assertThat(sut.getNeighboursOfBox(7, 0), hasSize(3));
        assertThat(sut.getNeighboursOfBox(7, 7), hasSize(3));
    }

    @Test
    public void getNeighboursOfBox_atEdgeReturnsFive() {
        assertThat(sut.getNeighboursOfBox(1, 0), hasSize(5));
        assertThat(sut.getNeighboursOfBox(2, 0), hasSize(5));
        assertThat(sut.getNeighboursOfBox(3, 0), hasSize(5));
        assertThat(sut.getNeighboursOfBox(4, 0), hasSize(5));
        assertThat(sut.getNeighboursOfBox(5, 0), hasSize(5));
        assertThat(sut.getNeighboursOfBox(6, 0), hasSize(5));

        assertThat(sut.getNeighboursOfBox(1, 7), hasSize(5));
        assertThat(sut.getNeighboursOfBox(2, 7), hasSize(5));
        assertThat(sut.getNeighboursOfBox(3, 7), hasSize(5));
        assertThat(sut.getNeighboursOfBox(4, 7), hasSize(5));
        assertThat(sut.getNeighboursOfBox(5, 7), hasSize(5));
        assertThat(sut.getNeighboursOfBox(6, 7), hasSize(5));

        assertThat(sut.getNeighboursOfBox(0, 1), hasSize(5));
        assertThat(sut.getNeighboursOfBox(0, 2), hasSize(5));
        assertThat(sut.getNeighboursOfBox(0, 3), hasSize(5));
        assertThat(sut.getNeighboursOfBox(0, 4), hasSize(5));
        assertThat(sut.getNeighboursOfBox(0, 5), hasSize(5));
        assertThat(sut.getNeighboursOfBox(0, 6), hasSize(5));

        assertThat(sut.getNeighboursOfBox(7, 1), hasSize(5));
        assertThat(sut.getNeighboursOfBox(7, 2), hasSize(5));
        assertThat(sut.getNeighboursOfBox(7, 3), hasSize(5));
        assertThat(sut.getNeighboursOfBox(7, 4), hasSize(5));
        assertThat(sut.getNeighboursOfBox(7, 5), hasSize(5));
        assertThat(sut.getNeighboursOfBox(7, 6), hasSize(5));
    }

    @Test
    public void getNeighboursOfBox_atTheMiddleReturnsEight() {
        for (int x = 1; x <= 6; ++x) {
            for (int y = 1; y <= 6; ++y) {
                assertThat(sut.getNeighboursOfBox(x, y), hasSize(8));
            }
        }
    }

    @Test
    public void getNeighboursOfBox_throwsExceptionInNotInitialized() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Field not initialized! Invoke MineField#initializeFieldWithBoxes() first.");
        new MineField(1, 1).getNeighboursOfBox(0, 0);
    }

    @Test
    public void getNeighboursOfBox_throwsExceptionIfRowIdLessThanZero() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Row id must not be less than 0!");
        sut.getNeighboursOfBox(-1, 0);
    }

    @Test
    public void getNeighboursOfBox_throwsExceptionIfColumnIdLessThanZero() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Column id must not be less than 0!");
        sut.getNeighboursOfBox(0, -1);
    }

    @Test
    public void getBox_throwsExceptionInNotInitialized() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Field not initialized! Invoke MineField#initializeFieldWithBoxes() first.");
        new MineField(1, 1).getBox(0, 0);
    }

    @Test
    public void getBox_throwsExceptionIfXLessThanZero() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("X must not be less than 0!");
        sut.getBox(-1, 0);
    }

    @Test
    public void getBox_throwsExceptionIfXGreaterThanWidthMinusOne() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("X must not be less than MineField#getWidth()!");
        sut.getBox(8, 0);
    }

    @Test
    public void getBox_throwsExceptionIfYLessThanZero() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Y must not be less than 0!");
        sut.getBox(0, -1);
    }

    @Test
    public void getBox_throwsExceptionIfYGreaterThanWidthMinusOne() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Y must not be less than MineField#getHeight()!");
        sut.getBox(0, 8);
    }

    @Test
    public void getBox() {
        assertThat(sut.getBox(0, 0).getY(), is(0));
        assertThat(sut.getBox(0, 0).getX(), is(0));
        assertThat(sut.getBox(5, 3).getY(), is(3));
        assertThat(sut.getBox(5, 3).getX(), is(5));
        assertThat(sut.getBox(3, 5).getY(), is(5));
        assertThat(sut.getBox(3, 5).getX(), is(3));
    }

    @Test
    public void allBoxesOpenOrFlagged_emptyBoxes() {
        final List<FieldBox> boxes = new ArrayList<FieldBox>();
        assertThat(MineField.allBoxesOpenOrFlagged(boxes), is(true));
    }

    @Test
    public void allBoxesOpenOrFlagged_allAreClosed() {
        final List<FieldBox> boxes = Arrays.asList(
                createClosedBox(),
                createClosedBox(),
                createClosedBox(),
                createClosedBox(),
                createClosedBox(),
                createClosedBox());
        assertThat(MineField.allBoxesOpenOrFlagged(boxes), is(false));
    }

    @Test
    public void allBoxesOpenOrFlagged_someBoxesAreOpened() {
        final List<FieldBox> boxes = Arrays.asList(
                createClosedBox(),
                createOpenBox(),
                createClosedBox(),
                createClosedBox(),
                createOpenBox(),
                createClosedBox());
        assertThat(MineField.allBoxesOpenOrFlagged(boxes), is(false));
    }

    @Test
    public void allBoxesOpenOrFlagged_allBoxesAreOpened() {
        final List<FieldBox> boxes = Arrays.asList(
                createOpenBox(),
                createOpenBox(),
                createOpenBox(),
                createOpenBox(),
                createOpenBox(),
                createOpenBox());
        assertThat(MineField.allBoxesOpenOrFlagged(boxes), is(true));
    }

    @Test
    public void allBoxesOpenOrFlagged_someBoxesAreFlagged() {
        final List<FieldBox> boxes = Arrays.asList(
                createClosedBox(),
                createFlagBox(),
                createClosedBox(),
                createClosedBox(),
                createFlagBox(),
                createClosedBox());
        assertThat(MineField.allBoxesOpenOrFlagged(boxes), is(false));
    }

    @Test
    public void allBoxesOpenOrFlagged_allBoxesAreFlagged() {
        final List<FieldBox> boxes = Arrays.asList(
                createFlagBox(),
                createFlagBox(),
                createFlagBox(),
                createFlagBox(),
                createFlagBox(),
                createFlagBox());
        assertThat(MineField.allBoxesOpenOrFlagged(boxes), is(true));
    }

    @Test
    public void allBoxesOpenOrFlagged_someBoxesAreOpenedAndFlagged() {
        final List<FieldBox> boxes = Arrays.asList(
                createClosedBox(),
                createOpenBox(),
                createClosedBox(),
                createClosedBox(),
                createFlagBox(),
                createClosedBox());
        assertThat(MineField.allBoxesOpenOrFlagged(boxes), is(false));
    }

    public void allBoxesOpenOrFlagged_allBoxesAreOpenedAndFlagged() {
        final List<FieldBox> boxes = Arrays.asList(
                createOpenBox(),
                createFlagBox(),
                createOpenBox(),
                createFlagBox(),
                createOpenBox(),
                createFlagBox());
        assertThat(MineField.allBoxesOpenOrFlagged(boxes), is(true));
    }
}
