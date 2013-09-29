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
package de.weltraumschaf.minesweeper;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Tests for {@link Matrix}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MatrixTest {

    //CHECKSTYLE:OFF
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    //CHECKSTYLE:ON
    private final Matrix<Object> sut = new Matrix<Object>(Object.class, 3, 4);

    @Test
    public void createThrowsExceptionIfTypeIsNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Type must not be null!");
        new Matrix<Object>(null, 1, 1);
    }

    @Test
    public void createThrowsExceptionIfWidthIsLessThanOne() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Width must not be less than 1!");
        new Matrix<Object>(Object.class, 0, 1);
    }

    @Test
    public void createThrowsExceptionIfHeightIsLessThanOne() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Height must not be less than 1!");
        new Matrix<Object>(Object.class, 1, 0);
    }

    @Test
    public void getWidth() {
        assertThat(sut.getWidth(), is(3));
    }

    @Test
    public void getHeight() {
        assertThat(sut.getHeight(), is(4));
    }

    @Test
    public void getSize() {
        assertThat(sut.size(), is(12));
    }

    @Test
    public void get_throwsExceptionIfXLessThanZero() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("X must not be less than 0!");
        sut.get(-1, 0);
    }

    @Test
    public void get_throwsExceptionIfXGreaterThanWidthMinusOne() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("X must be less than 3!");
        sut.get(3, 0);
    }

    @Test
    public void get_throwsExceptionIfYLessThanZero() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Y must not be less than 0!");
        sut.get(0, -1);
    }

    @Test
    public void get_throwsExceptionIfYGreaterThanWidthMinusOne() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Y must be less than 4!");
        sut.get(0, 4);
    }

    @Test
    public void set_throwsExceptionIfXLessThanZero() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("X must not be less than 0!");
        sut.set(-1, 0, new Object());
    }

    @Test
    public void set_throwsExceptionIfXGreaterThanWidthMinusOne() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("X must be less than 3!");
        sut.set(3, 0, new Object());
    }

    @Test
    public void set_throwsExceptionIfYLessThanZero() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Y must not be less than 0!");
        sut.set(0, -1, new Object());
    }

    @Test
    public void set_throwsExceptionIfYGreaterThanWidthMinusOne() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Y must be less than 4!");
        sut.set(0, 4, new Object());
    }

    @Test
    public void set_throwsExceptionIfDatumIsNull() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("Datum must not be null!");
        sut.set(0, 0, null);
    }

    @Test
    public void setAndGet() {
        assertThat(sut.get(0, 0), is(nullValue()));
        final Object obj1 = new Object();
        sut.set(0, 0, obj1);
        assertThat(sut.get(0, 0), is(sameInstance(obj1)));

        assertThat(sut.get(2, 2), is(nullValue()));
        final Object obj2 = new Object();
        sut.set(2, 2, obj2);
        assertThat(sut.get(2, 2), is(sameInstance(obj2)));

        assertThat(sut.get(2, 3), is(nullValue()));
        final Object obj3 = new Object();
        sut.set(2, 3, obj3);
        assertThat(sut.get(2, 3), is(sameInstance(obj3)));
    }

    @Test
    public void getAll() {
        assertThat(sut.getAll(), hasSize(0));
        sut.initWithObjects();
        assertThat(sut.getAll(), hasSize(sut.size()));

        for (final Object o : sut.getAll()) {
            assertThat(o, is(notNullValue()));
        }
    }

    @Test
    public void initWithObjects_throwsExceptionIfTypeHasNoNonArgconstructor() {
        final Matrix<CllassWithArgumentConstructor> innerSut =
                new Matrix<CllassWithArgumentConstructor>(CllassWithArgumentConstructor.class, 1, 1);
        thrown.expect(IllegalStateException.class);
        innerSut.initWithObjects();
    }

    @Test
    public void getRows() {
        final Matrix<String> innerSut = new Matrix<String>(String.class, 3, 3);
        innerSut.set(0, 0, "0");
        innerSut.set(1, 0, "1");
        innerSut.set(2, 0, "2");
        innerSut.set(0, 1, "3");
        innerSut.set(1, 1, "4");
        innerSut.set(2, 1, "5");
        innerSut.set(0, 2, "6");
        innerSut.set(1, 2, "7");
        innerSut.set(2, 2, "8");

        final List<List<String>> rows = innerSut.getRows();
        assertThat(rows, hasSize(3));
        assertThat(rows.get(0), hasSize(3));
        assertThat(rows.get(0), contains("0", "1", "2"));
        assertThat(rows.get(1), hasSize(3));
        assertThat(rows.get(1), contains("3", "4", "5"));
        assertThat(rows.get(2), hasSize(3));
        assertThat(rows.get(2), contains("6", "7", "8"));
    }

    @Test
    public void testToString() {
        final Matrix<String> innerSut = new Matrix<String>(String.class, 3, 2);
        innerSut.set(0, 0, "0");
        innerSut.set(1, 0, "1");
        innerSut.set(2, 0, "2");
        innerSut.set(0, 1, "3");
        innerSut.set(1, 1, "4");
        innerSut.set(2, 1, "5");
        assertThat(innerSut.toString(), is(equalTo(String.format(
                "(0) (1) (2) %n"
                + "(3) (4) (5) %n"))));
    }

    private static final class CllassWithArgumentConstructor {

        CllassWithArgumentConstructor(final String bar) {
            super();
        }
    }
}
