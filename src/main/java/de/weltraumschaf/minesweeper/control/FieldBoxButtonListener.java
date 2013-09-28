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
import de.weltraumschaf.minesweeper.gui.MainWindow;
import de.weltraumschaf.minesweeper.model.FieldBox;
import de.weltraumschaf.minesweeper.model.MineField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.apache.commons.lang3.Validate;
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
    private static final Logger LOG = Logger.getLogger(FieldBoxButtonListener.class);
    /**
     * Frame to which the listener was added.
     */
    private final MainWindow main;

    /**
     * Dedicated constructor.
     *
     * @param main must not be {@code null}
     */
    public FieldBoxButtonListener(final MainWindow main) {
        super();
        Validate.notNull(main, "Main must not be null!");
        this.main = main;
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        if (!(e.getComponent() instanceof FieldBoxButton)) {
            LOG.warn("Got click event from something else than FieldBoxButton!");
            return;
        }

        final FieldBoxButton originatingButton = (FieldBoxButton) e.getComponent();

        if (originatingButton.isOpen()) {
            LOG.debug(String.format("Button %s already opened, exit.", originatingButton));
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
                LOG.debug(String.format("Button %s flagged, exit.", originatingButton));
                return;
            }

            originatingButton.open();
        }

        originatingButton.getParent().repaint();
        checkForWon(originatingButton.getBox().getField());
    }

    private void checkForWon(final MineField field) {
        LOG.debug("Check if game was won.");

        if (LOG.isDebugEnabled()) {
            debugFieldSate(field);
        }

        if (field.hasWon()) {
            LOG.debug("Game won!");
            JOptionPane.showMessageDialog(
            main,
            String.format("Game won!"),
            main.getTitle(),
            JOptionPane.PLAIN_MESSAGE);
            openFlaggedBoxes(field);
        }
    }

    private void debugFieldSate(final MineField field) {
        final StringBuilder buffer = new StringBuilder("State of field:\n");

        for (final List<FieldBox> row : field.getBoxes().getRows()) {
            for (final FieldBox box : row) {
                buffer.append('(').append(box.toString()).append(' ');

                if (box.isOpen()) {
                    buffer.append("OPEN ");
                } else if (box.isFlag()) {
                    buffer.append("FLAG ");
                } else {
                    buffer.append("CLOSE");
                }

                buffer.append(") ");
            }

            buffer.append('\n');
        }

        LOG.debug(buffer.toString());
    }

    private void openFlaggedBoxes(final MineField field) {
        for (final FieldBox box : field.getBoxes().getAll()) {
            if (box.isFlag()) {
                box.setOpened(true);
            }
        }
    }
}
