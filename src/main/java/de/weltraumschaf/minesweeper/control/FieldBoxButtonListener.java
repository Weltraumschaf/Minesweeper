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
package de.weltraumschaf.minesweeper.control;

import de.weltraumschaf.minesweeper.gui.FieldBoxButton;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 * Listen for mouse clicks on a {@link FieldBoxButton}.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class FieldBoxButtonListener extends MouseAdapter {

    /**
     * Log facility.
     */
    private static final Logger LOG = Logger.getLogger(FieldBoxButtonListener.class.getName());

    static {
        LOG.setLevel(Level.OFF);
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        if (!(e.getComponent() instanceof FieldBoxButton)) {
            return;
        }

        final FieldBoxButton originatingButton = (FieldBoxButton) e.getComponent();

        if (originatingButton.isOpen()) {
            return;
        }

        if (SwingUtilities.isRightMouseButton(e)) {
            LOG.info("Right click on " + originatingButton.getBox().toString());

            if (originatingButton.isFlag()) {
                originatingButton.close();
            } else {
                originatingButton.flag();
            }
        } else {
            LOG.info("Left click on " + originatingButton.getBox().toString());

            if (originatingButton.isFlag()) {
                return;
            }

            originatingButton.open();
        }

        if (originatingButton.getBox().getField().isGameOver()) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    for (final Component comp : originatingButton.getParent().getComponents()) {
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

}
