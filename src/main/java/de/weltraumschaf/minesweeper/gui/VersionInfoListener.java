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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.apache.commons.lang3.Validate;

/**
 * Listens for the menu item version and shows version info dialog.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class VersionInfoListener implements ActionListener{

    /**
     * Used to dispose.
     */
    private final MainWindow main;

    /**
     * Dedicated constructor.
     *
     * @param main must not be {@code nul;}
     */
    public VersionInfoListener(final MainWindow main) {
        super();
        Validate.notNull(main, "Main window must not be null!");
        this.main = main;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        
    }

}
