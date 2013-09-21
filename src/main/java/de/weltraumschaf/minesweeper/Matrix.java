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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.Validate;

/**
 * Represents a matrix with fixed width/height for generic type.
 *
 * @param <T> type of stored data
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Matrix<T> {

    /**
     * Contains the bare data.
     */
    private final T[][] data;
    /**
     * Width of matrix.
     */
    private final int width;
    /**
     * Height of matrix.
     */
    private final int height;
    /**
     * Type of stored data.
     */
    private final Class<T> type;

    /**
     * Dedicated constructor.
     *
     * @param type must not be {@code null}
     * @param width must not be less than 1
     * @param height must not be less than 1
     */
    @SuppressWarnings("unchecked") // Because of unchecked cast to create generic array.
    public Matrix(final Class<T> type, final int width, final int height) {
        super();
        Validate.notNull(type, "Type must not be null!");
        this.type = type;
        Validate.isTrue(width > 0, "Width must not be less than 1!");
        this.width = width;
        Validate.isTrue(height > 0, "Height must not be less than 1!");
        this.height = height;
        this.data = (T[][]) Array.newInstance(type, width, height);
    }

    /**
     * Get the width.
     *
     * @return greater than 0
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height.
     *
     * @return greater than 0
     */
    public int getHeight() {
        return height;
    }

    /**
     * Product of width and height.
     *
     * @return greater than 0
     */
    public int size() {
        return width * height;
    }

    /**
     * Get a collection of all non {@code null} data elements in the matrix.
     *
     * @return never {@code null}
     */
    public List<T> getAll() {
        final List<T> all = new ArrayList<T>(size());

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final T datum = get(x, y);

                if (null == datum) {
                    continue;
                }

                all.add(datum);
            }

        }

        return all;
    }

    /**
     * Get datum at position.
     *
     * @param x must not be less than 0 and not greater equal {@link #getWidth()}
     * @param y must not be less than 0 and not greater equal {@link #getHeight()}
     * @return may be {@code null}
     */
    public T get(final int x, final int y) {
        validateX(x);
        validateY(y);
        return data[x][y];
    }

    /**
     * Set datum at position.
     *
     * @param x must not be less than 0 and not greater equal {@link #getWidth()}
     * @param y must not be less than 0 and not greater equal {@link #getHeight()}
     * @param datum must not be {@code null}
     */
    public void set(final int x, final int y, T datum) {
        validateX(x);
        validateY(y);
        Validate.notNull(datum, "Datum must not be null!");
        data[x][y] = datum;
    }

    /**
     * Validates that x is valid range [0, width - 1] an will throw {@link IllegalArgumentException} if not.
     *
     * @param x any number
     */
    private void validateX(final int x) {
        Validate.isTrue(x >= 0, "X must not be less than 0!");
        Validate.isTrue(x < width, String.format("X must be less than %d!", width));
    }

    /**
     * Validates that y is valid range [0, height - 1] an will throw {@link IllegalArgumentException} if not.
     *
     * @param y any number
     */
    private void validateY(final int y) {
        Validate.isTrue(y >= 0, "Y must not be less than 0!");
        Validate.isTrue(y < height, String.format("Y must be less than %d!", height));
    }

    /**
     * Initializes all matrix fields with an new instance of type T.
     *
     * That this method works T must provide a non-argument constructor!
     */
    public void initWithObjects() {
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final T datum;

                try {
                    datum = type.newInstance();
                } catch (InstantiationException | IllegalAccessException ex) {
                    throw new IllegalStateException(ex);
                }

                set(x, y, datum);
            }
        }
    }

    /**
     * Get all data elements as list of rows.
     *
     * @return never {@code null}, no {@code null} values
     */
    public List<List<T>> getRows() {
        final List<List<T>> rows = new ArrayList<List<T>>(height);

        for (int y = 0; y < height; ++y) {
            final List<T> row = new ArrayList<T>(width);

            for (int x = 0; x < width; ++x) {
                final T datum = get(x, y);

                if (datum != null) {
                    row.add(datum);
                }
            }

            rows.add(row);
        }

        return rows;
    }
}
