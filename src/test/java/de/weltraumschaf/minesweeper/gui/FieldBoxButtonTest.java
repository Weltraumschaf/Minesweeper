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
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Ignore;
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
    private final FieldBoxButton sut = new FieldBoxButton();
    private final FieldBox box = mock(FieldBox.class);

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

    @Test @Ignore
    public void open() {
        // TODO: Implement test
    }

    @Test
    public void close_throwsExceptionIfBoxNotSet() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Box model is null! Set box first.");
        sut.setState(FieldBoxButton.State.OPEN);
        sut.close();
    }

    @Test @Ignore
    public void close() {
        // TODO: Implement test
    }

    @Test
    public void flag_throwsExceptionIfBoxNotSet() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Box model is null! Set box first.");
        sut.flag();
    }

    @Test @Ignore
    public void flag() {
        // TODO: Implement test
    }

    @Test @Ignore
    public void determineIcon() {
        // TODO: Implement test
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
