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
import java.util.Random;
import org.apache.commons.lang3.Validate;

/**
 * Represents a mine field which contains mine and save boxes randomly.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MineField {

    /**
     * One quarter mines.
     */
    private static final int MINE_FACTOR = 4;
    /**
     * Used to calculate percentage of mines and save boxes.
     */
    private static final double HUNDRED_PERCENT = 100.0;
    /**
     * Default Width.
     */
    private static final int DEFAULT_WIDTH = 8;
    /**
     * Default height.
     */
    private static final int DEFAULT_HEIGHT = 8;
    /**
     * How many boxes in the height.
     */
    private final int height;
    /**
     * How many boxes in the width.
     */
    private final int width;
    /**
     * The box matrix.
     */
    private final BaseMineFieldBox[][] boxes;
    /**
     * Used to initialize the box matrix randomly.
     */
    private final Random random = new Random();
    /**
     * Number of mine boxes.
     */
    private int minesCount;
    /**
     * Number of save boxes.
     */
    private int savesCount;
    /**
     * Whether a mine was opened and game is over.
     */
    private boolean gameOver;
    /**
     * Whether {@link #initializeFieldWithBoxes(int, int)} was invoked at least once.
     */
    private boolean initialized;

    /**
     * Initializes width and height with {@link #DEFAULT_WIDTH} and {@link #DEFAULT_HEIGHT}.
     */
    public MineField() {
        this(DEFAULT_HEIGHT, DEFAULT_WIDTH);
    }

    /**
     * Dedicated constructor.
     *
     * @param height must not be less than 1
     * @param width must not be less than 1
     */
    public MineField(final int height, final int width) {
        super();
        Validate.isTrue(height > 0, "Height must not be less than 1!");
        this.height = height;
        Validate.isTrue(width > 0, "Width must not be less than 1!");
        this.width = width;
        this.boxes = new BaseMineFieldBox[width][height];
    }

    /**
     * Set all fields in {@link #boxes} with an instance.
     *
     * @param x coordinate of first clicked box
     * @param y coordinate of first clicked box
     */
    public void initializeFieldWithBoxes(final int x, final int y) {
        for (int rowId = 0; rowId < width; ++rowId) {
            for (int columnId = 0; columnId < height; ++columnId) {
                boxes[rowId][columnId] = createRandomBox(rowId, columnId, x, y);
            }
        }

        initialized = true;
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(String.format("Mine filed (width: %s, height: %s)%n", height, width));

        for (final BaseMineFieldBox[] row : boxes) {
            for (final BaseMineFieldBox box : row) {
                buffer.append(box).append(' ');
            }

            buffer.append(String.format("%n"));
        }

        final double count = minesCount + savesCount;
        final double minesPercent = (HUNDRED_PERCENT / count) * minesCount;
        final double savesPercent = (HUNDRED_PERCENT / count) * savesCount;
        buffer.append(String.format("Mines: %s (%.2f%%), saves: %s (%.2f%%)%n",
                minesCount, minesPercent, savesCount, savesPercent));
        return buffer.toString();
    }

    /**
     * Create randomly a mine or save box.
     *
     * In average a quarter of created boxes is a mine.
     *
     * @param rowId must not be less than 0
     * @param columnId must not be less than 0
     * @return always new instance
     */
    private BaseMineFieldBox createRandomBox(final int rowId, final int columnId, final int x, final int y) {
        if (rowId == x && columnId == y) {
            ++savesCount;
            return new SaveBox(rowId, columnId, this);
        } else if (random.nextInt() % MINE_FACTOR == 0) {
            ++minesCount;
            return new MineBox(rowId, columnId, this);
        } else {
            ++savesCount;
            return new SaveBox(rowId, columnId, this);
        }
    }

    /**
     * Get the neighbors of a box.
     *
     * <pre>
     * +-----+-------+-----+
     * | 3,3 |  4,3  | 5,3 |
     * +-----+-------+-----+
     * | 3,4 | [4,4] | 5,4 |
     * +-----+-------+-----+
     * | 3,5 |  4,5  | 5,5 |
     * +-----+-------+-----+
     * </pre>
     *
     * Throws {@link IllegalStateException} if {@link #initializeFieldWithBoxes(int, int)} never called before.
     *
     * @param rowId must not be less than 0
     * @param columnId must not be less than 0
     * @return never {@code null}, maybe empty list
     */
    List<MineFieldBox> getNeighboursOfBox(final int rowId, final int columnId) {
        if (!initialized) {
            throw new IllegalStateException(
                    "Field not initialized! Invoke MineField#initializeFieldWithBoxes() first.");
        }

        Validate.isTrue(rowId >= 0, "Row id must not be less than 0!");
        Validate.isTrue(columnId >= 0, "Column id must not be less than 0!");
        int rowIdStart = rowId - 1;

        if (rowIdStart < 0) {
            rowIdStart = 0;
        }

        int columnIdStart = columnId - 1;

        if (columnIdStart < 0) {
            columnIdStart = 0;
        }

        int rowIdStop = rowId + 1;

        if (rowIdStop > width - 1) {
            rowIdStop = width - 1;
        }

        int columnIdStop = columnId + 1;

        if (columnIdStop > height - 1) {
            columnIdStop = height - 1;
        }

        final List<MineFieldBox> neighbours = new ArrayList<MineFieldBox>();

        for (int x = rowIdStart; x <= rowIdStop; ++x) {
            for (int y = columnIdStart; y <= columnIdStop; ++y) {
                if (x == rowId && y == columnId) {
                    continue; // Ignore self.
                }

                neighbours.add(boxes[x][y]);
            }
        }

        return neighbours;
    }

    /**
     * Get width.
     *
     * @return never less than 1
     */
    public int getWidth() {
        return height;
    }

    /**
     * Get height.
     *
     * @return never less than 1
     */
    public int getHeight() {
        return width;
    }

    /**
     * Get the field box a given position.
     *
     * Throws {@link IllegalStateException} if {@link #initializeFieldWithBoxes(int, int)} never called before.
     *
     * @param x must not be less than 0 and greater than {@link #getWidth()} - 1.
     * @param y must not be less than 0 and greater than {@link #getHeight()} - 1.
     * @return never {@code null}
     */
    public MineFieldBox getBox(final int x, final int y) {
        if (!initialized) {
            throw new IllegalStateException(
                    "Field not initialized! Invoke MineField#initializeFieldWithBoxes() first.");
        }

        Validate.isTrue(x >= 0, "X must not be less than 0!");
        Validate.isTrue(x < width, "X must not be less than MineField#getWidth()!");
        Validate.isTrue(y >= 0, "Y must not be less than 0!");
        Validate.isTrue(y < height, "Y must not be less than MineField#getHeight()!");
        return boxes[x][y];
    }

    public void setGameOver() {
        gameOver = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean hasWon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
