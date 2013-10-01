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

import de.weltraumschaf.minesweeper.gui.Size;
import de.weltraumschaf.minesweeper.model.MinesweeperSession;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.apache.log4j.Logger;

/**
 * Resizes the mine field.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class ResizeFieldListener implements ActionListener {

    /**
     * Log facility.
     */
    private static final Logger LOG = Logger.getLogger(MinesweeperSession.class);

    @Override
    public void actionPerformed(final ActionEvent e) {
        final Size size = Size.getForActioncommand(e.getActionCommand());
        LOG.debug(String.format("Resize mine field to %s.", size));
    }

}
