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

import de.weltraumschaf.minesweeper.gui.MainWindow;
import de.weltraumschaf.minesweeper.gui.Size;
import de.weltraumschaf.minesweeper.model.Game;
import de.weltraumschaf.minesweeper.model.MinesweeperSession;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.apache.commons.lang3.Validate;
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
    /**
     * Used to get current game.
     */
    private final MinesweeperSession session;
    private final MainWindow main;

    /**
     * Dedicated constructor.
     *
     * @param session must not be {@code null}
     * @param main must not be {@code null}
     */
    public ResizeFieldListener(final MinesweeperSession session, final MainWindow main) {
        super();
        Validate.notNull(session, "Session must not be null!");
        this.session = session;
        Validate.notNull(main, "Main must not be null!");
        this.main = main;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final Size size = Size.getForActioncommand(e.getActionCommand());
        LOG.debug(String.format("Resize mine field to %s.", size));
        final Game game = session.getCurrentGame();
        game.stop();
        game.resize(size.getWidth(), size.getHeight());
        main.setMineField(game.getMineField());
        game.start();
    }

}
