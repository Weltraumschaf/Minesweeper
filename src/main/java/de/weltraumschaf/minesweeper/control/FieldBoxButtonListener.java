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
import de.weltraumschaf.minesweeper.model.MineField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

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
            LOG.warn("Got click event from something else than FieldBoxButton!");
            return;
        }

        final FieldBoxButton originatingButton = (FieldBoxButton) e.getComponent();

        if (originatingButton.isOpen()) {
            checkForWon(originatingButton.getBox().getField());
            return;
        }

        if (SwingUtilities.isRightMouseButton(e)) {
            LOG.debug(String.format("Right click on %s.", originatingButton.getBox()));

            if (originatingButton.isFlag()) {
                originatingButton.close();
            } else {
                originatingButton.flag();
            }
        } else {
            LOG.debug(String.format("Left click on %s", originatingButton.getBox()));

            if (originatingButton.isFlag()) {
                checkForWon(originatingButton.getBox().getField());
                return;
            }

            originatingButton.open();
        }

        originatingButton.getParent().repaint();
    }

    private void checkForWon(final MineField field) {
        LOG.debug("Check if game was won.");

        if (field.hasWon()) {
            LOG.debug("Game won!");

            for (final FieldBox box : field.getBoxes().getAll()) {
                if (box.isFlag()) {
                    box.setOpened();
                }
            }
        }
    }

}
