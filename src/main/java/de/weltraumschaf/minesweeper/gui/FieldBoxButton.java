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
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import org.apache.commons.lang3.Validate;

/**
 * Represents a mine filed box button.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class FieldBoxButton extends JButton implements Observer {
    /**
     * Log facility.
     */
    private static final Logger LOG = Logger.getLogger(FieldBoxButton.class.getName());
    /**
     * Zero mines in neighborhood.
     */
    private static final int ZERO_NEIGHBORS = 0;
    /**
     * One mines in neighborhood.
     */
    private static final int ONE_NEIGHBORS = 1;
    /**
     * Two mines in neighborhood.
     */
    private static final int TWO_NEIGHBORS = 2;
    /**
     * Three mines in neighborhood.
     */
    private static final int THREE_NEIGHBORS = 3;
    /**
     * Four mines in neighborhood.
     */
    private static final int FOUR_NEIGHBORS = 4;
    /**
     * Five mines in neighborhood.
     */
    private static final int FIVE_NEIGHBORS = 5;
    /**
     * Six mines in neighborhood.
     */
    private static final int SIX_NEIGHBORS = 6;
    /**
     * Seven mines in neighborhood.
     */
    private static final int SEVEN_NEIGHBORS = 7;
    /**
     * Eight mines in neighborhood.
     */
    private static final int EIGHT_NEIGHBORS = 8;
    /**
     * The data model behind the button.
     */
    private final MineFieldBox box;

    static {
        LOG.setLevel(Level.ALL);
    }

    /**
     * Dedicated constructor.
     *
     * Initializes the button with closed icon.
     *
     * @param box must not be {@code null}
     */
    public FieldBoxButton(MineFieldBox box) {
        super(ImageIcons.CLOSED.getResource());
        Validate.notNull(box, "Box must not be null!");
        this.box = box;
    }

    /**
     * Get the box model.
     *
     * @return never {@code null}
     */
    public MineFieldBox getBox() {
        return box;
    }

    /**
     * Open the box.
     */
    public void open() {
        if (box.isOpen()) {
            return;
        }

        if (box.isMine()) {
            setIcon(ImageIcons.BOMB_EXPLODED.getResource());
            box.getField().setGameOver();
        } else {
            setIcon(determineIcon());

            if (box.countMinesInNeighborhood() == 0) {
                for (final MineFieldBox neighbor : box.getNeighbours()) {
                    neighbor.setOpened(true);
                }
            }
        }

        repaint();
    }

    /**
     * Whether the box is open or not.
     *
     * @return {@code true} if the box is already open
     */
    public boolean isOpen() {
        return box.isOpen();
    }

    /**
     * Close the box.
     *
     * Already opened box can not be closed.
     */
    public void close() {
        if (!box.isOpen()) {
            return;
        }

        box.setFlagged(false);
        setIcon(ImageIcons.CLOSED.getResource());
        repaint();
    }

    public void flag() {
        if (box.isFlag()) {
            return;
        }

        box.setFlagged(true);
        setIcon(ImageIcons.FLAG.getResource());
        repaint();
    }

    public boolean isFlag() {
        return box.isFlag();
    }

    private ImageIcon determineIcon() {
        final ImageIcon icon;

        if (box.isMine()) {
            icon = ImageIcons.BOMB.getResource();
        } else {
            icon = determineNumberIcon();
        }

        return icon;
    }

    private ImageIcon determineNumberIcon() {
        final ImageIcon icon;

        switch (box.countMinesInNeighborhood()) {
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
                        box.countMinesInNeighborhood()));
        }

        return icon;
    }

    @Override
    public void update(final Observable observable, final Object arg) {
        if (box.isOpen()) {
            open();
        }
    }
}
