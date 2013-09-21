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
import de.weltraumschaf.minesweeper.model.MineField;
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
     * Frame to which the listener was added.
     *
     * XXX: Consider if retrievable from {@link ActionEvent}.
     */
    private final MainWindow main;

    /**
     * Dedicated constructor.
     *
     * @param main must not be {@code null}
     */
    public NewGameListener(final MainWindow main) {
        super();
        Validate.notNull(main, "Main must not be null!");
        this.main = main;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final MineField newField = new MineField(main.getMineField().getWidth(), main.getMineField().getHeight());
        newField.initializeFieldWithBoxes(0, 0);
        main.setMineField(newField);
        main.initPanel();
        main.repaint();
    }

}
