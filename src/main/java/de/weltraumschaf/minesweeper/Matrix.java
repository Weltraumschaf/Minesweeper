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
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.Validate;

/**
 * Represents a matrix with rows and columns for generic type.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class Matrix<T> {

    private final T[][] data;
    private final int width;
    private final int height;
    private final Class<T> type;

    @SuppressWarnings({"unchecked"})
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int size() {
        return width * height;
    }

    public Collection<T> getAll() {
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

    public T get(final int x, final int y) {
        validateX(x);
        validateY(y);
        return data[x][y];
    }

    public void set(final int x, final int y, T datum) {
        validateX(x);
        validateY(y);
        Validate.notNull(datum, "Datum must not be null!");
        data[x][y] = datum;
    }

    private void validateX(final int x) {
        Validate.isTrue(x >= 0, "X must not be less than 0!");
        Validate.isTrue(x < width, String.format("X must be less than %d!", width));
    }

    private void validateY(final int y) {
        Validate.isTrue(y >= 0, "Y must not be less than 0!");
        Validate.isTrue(y < height, String.format("Y must be less than %d!", height));
    }

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
}
