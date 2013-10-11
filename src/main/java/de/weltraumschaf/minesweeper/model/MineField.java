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

import de.weltraumschaf.minesweeper.Matrix;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

/**
 * Represents a mine field which contains mine and save boxes randomly.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MineField {

    /**
     * Default Width.
     */
    public static final int DEFAULT_WIDTH = 8;
    /**
     * Default height.
     */
    public static final int DEFAULT_HEIGHT = 8;
    /**
     * Log facility.
     */
    private static final Logger LOG = Logger.getLogger(MineField.class);
    /**
     * 1/8 mines.
     */
    private static final int MINE_FACTOR = 8;
    /**
     * Used to calculate percentage of mines and save boxes.
     */
    private static final double HUNDRED_PERCENT = 100.0;
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
    private final Matrix<FieldBox> boxes;
    /**
     * Used to initialize the box matrix randomly.
     */
    private final Random random = new Random();
    /**
     * Game to which a field belongs.
     */
    private final Game game;
    /**
     * Whether {@link #initializeFieldWithBoxes()} was invoked at least once.
     */
    private boolean initialized;

    /**
     * Initializes width and height with {@link #DEFAULT_WIDTH} and {@link #DEFAULT_HEIGHT}.
     *
     * @param game must not be {@code null}
     */
    public MineField(final Game game) {
        this(DEFAULT_HEIGHT, DEFAULT_WIDTH, game);
    }

    /**
     * Dedicated constructor.
     *
     * @param height must not be less than 1
     * @param width must not be less than 1
     * @param game must not be {@code null}
     */
    public MineField(final int height, final int width, final Game game) {
        super();
        Validate.isTrue(height > 0, "Height must not be less than 1!");
        this.height = height;
        Validate.isTrue(width > 0, "Width must not be less than 1!");
        this.width = width;
        Validate.notNull(game, "Game must not be null!");
        this.game = game;
        this.boxes = new Matrix<FieldBox>(FieldBox.class, width, height);
    }

    /**
     * Set all fields in {@link #boxes} with an instance.
     */
    public void initializeFieldWithBoxes() {
        LOG.debug(String.format("Initialize field with boxes (%dx%d).", width, height));

        for (int rowId = 0; rowId < height; ++rowId) {
            for (int columnId = 0; columnId < width; ++columnId) {
                boxes.set(columnId, rowId, createRandomBox(columnId, rowId));
            }
        }

        setInitialized(true);
    }

    /**
     * Set the state to initialized.
     *
     * @param initialized {@code true} if boxes are initialized, else {@code false}
     */
    void setInitialized(final boolean initialized) {
        this.initialized = initialized;
    }

    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(String.format("Mine field (width: %s, height: %s)%n", getWidth(), getHeight()))
                .append(boxes.toString());

        int minesCount = 0;
        int savesCount = 0;

        for (final FieldBox box : boxes.getAll()) {
            if (box.isMine()) {
                ++minesCount;
            } else {
                ++savesCount;
            }
        }

        final double count = minesCount + savesCount;
        final double minesPercent = (HUNDRED_PERCENT / count) * minesCount;
        final double savesPercent = (HUNDRED_PERCENT / count) * savesCount;
        buffer.append(String.format(Locale.ENGLISH, "Mines: %s (%.2f %%), saves: %s (%.2f %%)",
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
    private BaseMineFieldBox createRandomBox(final int rowId, final int columnId) {
        if (random.nextInt() % MINE_FACTOR == 0) {
            return new MineBox(rowId, columnId, this);
        } else {
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
     * Throws {@link IllegalStateException} if {@link #initializeFieldWithBoxes()} never called before.
     *
     * @param rowId must not be less than 0
     * @param columnId must not be less than 0
     * @return never {@code null}, maybe empty list
     */
    List<FieldBox> getNeighboursOfBox(final int rowId, final int columnId) {
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

        final List<FieldBox> neighbours = new ArrayList<FieldBox>();

        for (int x = rowIdStart; x <= rowIdStop; ++x) {
            for (int y = columnIdStart; y <= columnIdStop; ++y) {
                if (x == rowId && y == columnId) {
                    continue; // Ignore self.
                }

                neighbours.add(boxes.get(x, y));
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
        return width;
    }

    /**
     * Get height.
     *
     * @return never less than 1
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the field box a given position.
     *
     * Throws {@link IllegalStateException} if {@link #initializeFieldWithBoxes()} never called before.
     *
     * @param x must not be less than 0 and greater than {@link #getWidth()} - 1.
     * @param y must not be less than 0 and greater than {@link #getHeight()} - 1.
     * @return never {@code null}
     */
    public FieldBox getBox(final int x, final int y) {
        if (!initialized) {
            throw new IllegalStateException(
                    "Field not initialized! Invoke MineField#initializeFieldWithBoxes() first.");
        }

        return boxes.get(x, y);
    }

    /**
     * Set the field box a given position.
     *
     * @param x must not be less than 0 and greater than {@link #getWidth()} - 1.
     * @param y must not be less than 0 and greater than {@link #getHeight()} - 1.
     * @param box must not be {@code null}
     */
    void setBox(final int x, final int y, final FieldBox box) {
        boxes.set(x, y, box);
    }

    /**
     * Get the boxes.
     *
     * @return never {@code null}
     */
    public Matrix<FieldBox> getBoxes() {
        return boxes;
    }

    /**
     * Determines if all {@link FieldBox boxes} of the field are opened or flagged.
     *
     * @return {@code true} if all boxes are opened/flagged, else {@code false}
     */
    boolean allBoxesOpenOrFlagged() {
        return allBoxesOpenOrFlagged(boxes.getAll());
    }

    /**
     * Determines if all {@link FieldBox boxes} are opened or flagged.
     *
     * @param list must not be {@code null}
     * @return {@code true} if all boxes are opened/flagged, else {@code false}
     */
    static boolean allBoxesOpenOrFlagged(final List<FieldBox> list) {
        Validate.notNull(list, "List must not be null!");

        for (final FieldBox box : list) {
            if (!box.isOpen() && !box.isFlag()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns number of unflagged mines.
     *
     * @return not negative number
     */
    public int countUnflaggedMines() {
        int count = 0;

        for (final FieldBox box : boxes.getAll()) {
            if (box.isMine() && !box.isFlag()) {
                ++count;
            }
        }

        return count;
    }

    /**
     * Get associated game.
     *
     * @return never {@code null}
     */
    public Game getGame() {
        return game;
    }

}
