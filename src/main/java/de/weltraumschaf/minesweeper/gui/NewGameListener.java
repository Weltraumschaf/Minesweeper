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

import de.weltraumschaf.minesweeper.model.MineField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class NewGameListener implements ActionListener {

    private final MainWindow main;

    public NewGameListener(final MainWindow main) {
        super();
        this.main = main;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        final MineField newField = new MineField(main.getMineField().getWidth(), main.getMineField().getHeight());
        newField.initializeFieldWithBoxes();
        main.setMineField(newField);
        main.initPanel();
    }

}
