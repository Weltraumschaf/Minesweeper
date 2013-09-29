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

import de.weltraumschaf.minesweeper.model.MinesweeperSession;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.apache.commons.lang3.Validate;

/**
 * Listens for the new game menu item.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class NewGameListener implements ActionListener {

    /**
     * Current played session.
     */
    private final MinesweeperSession session;

    /**
     * Dedicated constructor.
     *
     * @param session must not be {@code null}
     */
    public NewGameListener(final MinesweeperSession session) {
        super();
        Validate.notNull(session, "Session must not be null!");
        this.session = session;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        session.newGame();
    }

}
