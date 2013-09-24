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

import de.weltraumschaf.minesweeper.GlobalLog;
import de.weltraumschaf.minesweeper.gui.FieldBoxButton;
import de.weltraumschaf.minesweeper.model.FieldBox;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private static final Logger LOG = GlobalLog.getLogger(FieldBoxButtonListener.class);

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
            LOG.info(String.format("Right click on %s.", originatingButton.getBox()));

            if (originatingButton.isFlag()) {
                originatingButton.close();
            } else {
                originatingButton.flag();
            }
        } else {
            LOG.info(String.format("Left click on %s", originatingButton.getBox()));

            if (originatingButton.isFlag()) {
                return;
            }

            originatingButton.open();
        }
    }

}
