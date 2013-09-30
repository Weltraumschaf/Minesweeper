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

    @Test
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

    @Test
    public void allBoxesOpenOrFlagged_memberBoxesAllClosed() {
        final MineField innerSut = new MineField(2, 2);
        innerSut.setInitialized(true);
        final SaveBox saveBox1 = new SaveBox(0, 0, innerSut);
        innerSut.setBox(0, 0, saveBox1);
        final SaveBox saveBox2 = new SaveBox(0, 1, innerSut);
        innerSut.setBox(1, 0, saveBox2);
        final SaveBox saveBox3 = new SaveBox(1, 0, innerSut);
        innerSut.setBox(0, 1, saveBox3);
        final SaveBox saveBox4 = new SaveBox(1, 1, innerSut);
        innerSut.setBox(1, 1, saveBox4);
        assertThat(innerSut.allBoxesOpenOrFlagged(), is(false));
    }

    @Test
    public void allBoxesOpenOrFlagged_memberBoxesAllOpened() {
        final MineField innerSut = new MineField(2, 2);
        innerSut.setInitialized(true);
        final SaveBox saveBox1 = new SaveBox(0, 0, innerSut);
        saveBox1.setOpened(true);
        innerSut.setBox(0, 0, saveBox1);
        final SaveBox saveBox2 = new SaveBox(0, 1, innerSut);
        saveBox2.setOpened(true);
        innerSut.setBox(1, 0, saveBox2);
        final SaveBox saveBox3 = new SaveBox(1, 0, innerSut);
        saveBox3.setOpened(true);
        innerSut.setBox(0, 1, saveBox3);
        final SaveBox saveBox4 = new SaveBox(1, 1, innerSut);
        saveBox4.setOpened(true);
        innerSut.setBox(1, 1, saveBox4);
        assertThat(innerSut.allBoxesOpenOrFlagged(), is(true));
    }

    @Test
    public void testToString() {
        final MineField innerSut = new MineField(4, 3);
        innerSut.setInitialized(true);
        innerSut.setBox(0, 0, new MineBox(0, 0, innerSut));
        innerSut.setBox(1, 0, new SaveBox(0, 1, innerSut));
        innerSut.setBox(2, 0, new SaveBox(0, 2, innerSut));
        innerSut.setBox(0, 1, new SaveBox(1, 0, innerSut));
        innerSut.setBox(1, 1, new SaveBox(1, 1, innerSut));
        innerSut.setBox(2, 1, new SaveBox(1, 2, innerSut));
        innerSut.setBox(0, 2, new SaveBox(2, 0, innerSut));
        innerSut.setBox(1, 2, new SaveBox(2, 1, innerSut));
        innerSut.setBox(2, 2, new SaveBox(2, 2, innerSut));
        innerSut.setBox(0, 3, new SaveBox(3, 0, innerSut));
        innerSut.setBox(1, 3, new SaveBox(3, 1, innerSut));
        innerSut.setBox(2, 3, new SaveBox(3, 2, innerSut));
        assertThat(innerSut.toString(), is(equalTo(String.format("Mine field (width: 3, height: 4)%n"
                + "(\uD83D\uDCA3 [0,0] CLOSE) (1 [0,1] CLOSE) (0 [0,2] CLOSE) %n"
                + "(1 [1,0] CLOSE) (1 [1,1] CLOSE) (0 [1,2] CLOSE) %n"
                + "(0 [2,0] CLOSE) (0 [2,1] CLOSE) (0 [2,2] CLOSE) %n"
                + "(0 [3,0] CLOSE) (0 [3,1] CLOSE) (0 [3,2] CLOSE) %n"
                + "Mines: 1 (8.33 %%), saves: 11 (91.67 %%)"))));
    }

    @Test
    public void setGameOver() {
        final MineField innerSut = new MineField(2, 2);
        innerSut.setInitialized(true);
        final SaveBox saveBox1 = spy(new SaveBox(0, 0, innerSut));
        innerSut.setBox(0, 0, saveBox1);
        final SaveBox saveBox2 = spy(new SaveBox(0, 1, innerSut));
        innerSut.setBox(1, 0, saveBox2);
        final SaveBox saveBox3 = spy(new SaveBox(1, 0, innerSut));
        innerSut.setBox(0, 1, saveBox3);
        final SaveBox saveBox4 = spy(new SaveBox(1, 1, innerSut));
        innerSut.setBox(1, 1, saveBox4);

        assertThat(innerSut.isGameOver(), is(false));
        innerSut.setGameOver();
        assertThat(innerSut.isGameOver(), is(true));

        verify(saveBox1, times(1)).setOpened(true);
        verify(saveBox2, times(1)).setOpened(true);
        verify(saveBox3, times(1)).setOpened(true);
        verify(saveBox4, times(1)).setOpened(true);
    }

    @Test
    public void hasWon_gameOver() {
        final MineField innerSut = new MineField(2, 2);
        innerSut.setInitialized(true);
        final SaveBox saveBox1 = new SaveBox(0, 0, innerSut);
        innerSut.setBox(0, 0, saveBox1);
        final SaveBox saveBox2 = new SaveBox(0, 1, innerSut);
        innerSut.setBox(1, 0, saveBox2);
        final SaveBox saveBox3 = new SaveBox(1, 0, innerSut);
        innerSut.setBox(0, 1, saveBox3);
        final SaveBox saveBox4 = new SaveBox(1, 1, innerSut);
        innerSut.setBox(1, 1, saveBox4);

        assertThat(innerSut.hasWon(), is(false));
        assertThat(innerSut.isGameOver(), is(false));
        innerSut.setGameOver();
        assertThat(innerSut.hasWon(), is(false));
        assertThat(innerSut.isGameOver(), is(true));
    }

    @Test
    public void hasWon_notGameOverAllClosed() {
        final MineField innerSut = new MineField(2, 2);
        innerSut.setInitialized(true);
        final SaveBox saveBox1 = new SaveBox(0, 0, innerSut);
        innerSut.setBox(0, 0, saveBox1);
        final SaveBox saveBox2 = new SaveBox(0, 1, innerSut);
        innerSut.setBox(1, 0, saveBox2);
        final SaveBox saveBox3 = new SaveBox(1, 0, innerSut);
        innerSut.setBox(0, 1, saveBox3);
        final SaveBox saveBox4 = new SaveBox(1, 1, innerSut);
        innerSut.setBox(1, 1, saveBox4);

        assertThat(innerSut.hasWon(), is(false));
        assertThat(innerSut.isGameOver(), is(false));
    }

    @Test
    public void hasWon_notGameOverAllOpen() {
        final MineField innerSut = new MineField(2, 2);
        innerSut.setInitialized(true);
        final SaveBox saveBox1 = new SaveBox(0, 0, innerSut);
        saveBox1.setOpened(true);
        innerSut.setBox(0, 0, saveBox1);
        final SaveBox saveBox2 = new SaveBox(0, 1, innerSut);
        saveBox2.setOpened(true);
        innerSut.setBox(1, 0, saveBox2);
        final SaveBox saveBox3 = new SaveBox(1, 0, innerSut);
        saveBox3.setOpened(true);
        innerSut.setBox(0, 1, saveBox3);
        final SaveBox saveBox4 = new SaveBox(1, 1, innerSut);
        saveBox4.setOpened(true);
        innerSut.setBox(1, 1, saveBox4);

        assertThat(innerSut.hasWon(), is(true));
        assertThat(innerSut.isGameOver(), is(false));
    }

    @Test
    public void countUnflaggedMines() {
        final MineField innerSut = new MineField(2, 2);
        innerSut.setInitialized(true);
        assertThat(innerSut.countUnflaggedMines(), is(0));
        
        final SaveBox box1 = new SaveBox(0, 0, innerSut);
        box1.setOpened(true);
        innerSut.setBox(0, 0, box1);
        final MineBox box2 = new MineBox(0, 1, innerSut);
        box2.setOpened(true);
        innerSut.setBox(1, 0, box2);
        final MineBox box3 = new MineBox(1, 0, innerSut);
        box3.setOpened(true);
        innerSut.setBox(0, 1, box3);
        final SaveBox box4 = new SaveBox(1, 1, innerSut);
        box4.setOpened(true);
        innerSut.setBox(1, 1, box4);

        assertThat(innerSut.countUnflaggedMines(), is(2));
        box2.setFlag(true);
        assertThat(innerSut.countUnflaggedMines(), is(1));
        box3.setFlag(true);
        assertThat(innerSut.countUnflaggedMines(), is(0));
    }

}
