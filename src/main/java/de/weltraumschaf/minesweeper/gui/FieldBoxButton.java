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

import de.weltraumschaf.minesweeper.model.MineFieldBox;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class FieldBoxButton extends JButton {
    private static final int ZERO_NEIGHBORS = 0;
    private static final int ONE_NEIGHBORS = 1;
    private static final int TWO_NEIGHBORS = 2;
    private static final int THREE_NEIGHBORS = 3;
    private static final int FOUR_NEIGHBORS = 4;
    private static final int FIVE_NEIGHBORS = 5;
    private static final int SIX_NEIGHBORS = 6;
    private static final int SEVEN_NEIGHBORS = 7;
    private static final int EIGHT_NEIGHBORS = 8;

    private final MineFieldBox model;

    public FieldBoxButton(MineFieldBox model) {
        super(ImageIcons.CLOSED.getResource());
        this.model = model;
    }

    public void open() {
        if (model.isOpened()) {
            return;
        }

        if (model.isMine()) {
            setIcon(ImageIcons.BOMB_EXPLODED.getResource());
            model.getField().setGameOver();
        } else {
            setIcon(determineIcon());

            if (model.countMinesInNeighborhood() == 0) {
            }
        }
    }

    public void close() {
        if (!model.isOpened()) {
            return;
        }

        model.setFlagged(false);
        setIcon(ImageIcons.CLOSED.getResource());
    }

    public void flag() {
        if (model.isFlagged()) {
            return;
        }

        model.setFlagged(true);
        setIcon(ImageIcons.FLAG.getResource());
    }

    private ImageIcon determineIcon() {
        final ImageIcon icon;

        if (model.isMine()) {
            icon = ImageIcons.BOMB.getResource();
        } else {
            icon = determineNumberIcon();
        }

        return icon;
    }

    private ImageIcon determineNumberIcon() {
        final ImageIcon icon;

        switch (model.countMinesInNeighborhood()) {
            case ZERO_NEIGHBORS:
                icon = ImageIcons.BLANK.getResource();
                break;
            case ONE_NEIGHBORS:
                icon = ImageIcons.ONE_NEIGHBOR.getResource();
                break;
            case TWO_NEIGHBORS:
                icon = ImageIcons.TWO_NEIGHBOR.getResource();
                break;
            case THREE_NEIGHBORS:
                icon = ImageIcons.THREE_NEIGHBOR.getResource();
                break;
            case FOUR_NEIGHBORS:
                icon = ImageIcons.FOUR_NEIGHBOR.getResource();
                break;
            case FIVE_NEIGHBORS:
                icon = ImageIcons.FIVE_NEIGHBOR.getResource();
                break;
            case SIX_NEIGHBORS:
                icon = ImageIcons.SIX_NEIGHBOR.getResource();
                break;
            case SEVEN_NEIGHBORS:
                icon = ImageIcons.SEVEN_NEIGHBOR.getResource();
                break;
            case EIGHT_NEIGHBORS:
                icon = ImageIcons.EIGHT_NEIGHBOR.getResource();
                break;
            default:
                throw new IllegalStateException(String.format("Unsupported neighbor count: %d!",
                        model.countMinesInNeighborhood()));
        }

        return icon;
    }
}
