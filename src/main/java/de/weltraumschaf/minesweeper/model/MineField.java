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

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MineField {
    /**
     * One quarter mines.
     */
    private static final int MINE_FACTOR = 4;

    private final int height;
    private final int width;
    private final BaseMineFieldBox[][] boxes;
    private final Random random = new Random();
    private int bombsCount;
    private int savesCount;
    private boolean gameOver;

    public MineField(final int height, final int width) {
        this.height = height;
        this.width = width;
        this.boxes = new BaseMineFieldBox[width][height];
    }

    public void initializeFieldWithBoxes() {
        for (int rowId = 0; rowId < width; ++rowId) {
            for (int columnId = 0; columnId < height; ++columnId) {
                boxes[rowId][columnId] = createBox(rowId, columnId);
            }
        }
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

        final double count = bombsCount + savesCount;
        final double bombsPercent = (100.0 / count) * bombsCount;
        final double savesPercent = (100.0 / count) * savesCount;
        buffer.append(String.format("Bombs: %s (%.2f%%), saves: %s (%.2f%%)%n",
                bombsCount, bombsPercent, savesCount, savesPercent));
        return buffer.toString();
    }

    private BaseMineFieldBox createBox(final int rowId, final int columnId) {
        if (random.nextInt() % MINE_FACTOR == 0) {
            ++bombsCount;
            return new MineBox(rowId, columnId, this);
        } else {
            ++savesCount;
            return new SaveBox(rowId, columnId, this);
        }
    }

    /**
     * Get the neighbor of a box.
     *
     * +-----+-------+-----+ | 3,3 | 4,3 | 5,3 | +-----+-------+-----+ | 3,4 | [4,4] | 5,4 | +-----+-------+-----+ | 3,5
     * | 4,5 | 5,5 | +-----+-------+-----+
     *
     * @param rowId
     * @param columnId
     * @return
     */
    List<BaseMineFieldBox> getNeighboursOfBox(final int rowId, final int columnId) {
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

        final List<BaseMineFieldBox> neighbours = new ArrayList<BaseMineFieldBox>();

        for (int x = rowIdStart; x <= rowIdStop; ++x) {
            for (int y = columnIdStart; y <= columnIdStop; ++y) {
                if (x == rowId && y == columnId) {
                    continue;
                }

                neighbours.add(boxes[x][y]);
            }
        }

        return neighbours;
    }

    public int getWidth() {
        return height;
    }

    public int getHeight() {
        return width;
    }

    public BaseMineFieldBox getBox(final int x, final int y) {
        return boxes[x][y];
    }

    public void setGameOver() {
        gameOver = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

}
