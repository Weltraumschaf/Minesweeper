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
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class BoxButtonListener extends MouseAdapter {

    private static final Logger LOG = Logger.getLogger(BoxButtonListener.class.getName());

    static {
        LOG.setLevel(Level.OFF);
    }
    private final MineFieldBox box;

    public BoxButtonListener(final MineFieldBox box) {
        super();
        this.box = box;
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        if (box.isOpened()) {
            return;
        }

        final JButton origin = (JButton) e.getComponent();

        if (SwingUtilities.isRightMouseButton(e)) {
            LOG.info("Right click on " + box.toString());

            if (box.isFlagged()) {
                origin.setIcon(ImageIcons.CLOSED.getResource());
                box.setFlagged(false);
            } else {
                origin.setIcon(ImageIcons.FLAG.getResource());
                box.setFlagged(true);
            }
        } else {
            LOG.info("Left click on " + box.toString());

            if (box.isFlagged()) {
                return;
            }

            box.setOpened(true);

            if (box.isMine()) {
                origin.setIcon(ImageIcons.BOMB_EXPLODED.getResource());
                box.getField().setGameOver();
            } else {
                origin.setIcon(determineIcon());

                if (box.countMinesInNeighborhood() == 0) {
                }
            }
        }

        if (box.getField().isGameOver()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    for (final Component comp : origin.getParent().getComponents()) {
                        if (comp instanceof JButton) {
                            final JButton button = (JButton) comp;
                            final MouseEvent me = new MouseEvent(button, // which
                                    MouseEvent.MOUSE_CLICKED, // what
                                    System.currentTimeMillis(), // when
                                    0, // no modifiers
                                    button.getX(), button.getY(), // where: at (10, 10}
                                    1, // only 1 click
                                    false); // not a popup trigger
                            button.dispatchEvent(me);
                        }
                    }
                }
            });
        }
    }

    private ImageIcon determineIcon() {
        final ImageIcon icon;

        if (box.isMine()) {
            icon = ImageIcons.BOMB.getResource();
        } else {
            switch (box.countMinesInNeighborhood()) {
                case 0:
                    icon = ImageIcons.BLANK.getResource();
                    break;
                case 1:
                    icon = ImageIcons.ONE_NEIGHBOR.getResource();
                    break;
                case 2:
                    icon = ImageIcons.TWO_NEIGHBOR.getResource();
                    break;
                case 3:
                    icon = ImageIcons.THREE_NEIGHBOR.getResource();
                    break;
                case 4:
                    icon = ImageIcons.FOUR_NEIGHBOR.getResource();
                    break;
                case 5:
                    icon = ImageIcons.FIVE_NEIGHBOR.getResource();
                    break;
                case 6:
                    icon = ImageIcons.SIX_NEIGHBOR.getResource();
                    break;
                case 7:
                    icon = ImageIcons.SEVEN_NEIGHBOR.getResource();
                    break;
                case 8:
                    icon = ImageIcons.EIGHT_NEIGHBOR.getResource();
                    break;
                default:
                    throw new IllegalStateException(String.format("Unsupported neighbor count: %d!",
                            box.countMinesInNeighborhood()));
            }
        }

        return icon;
    }

}
