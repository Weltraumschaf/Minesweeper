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

import de.weltraumschaf.commons.Version;
import de.weltraumschaf.minesweeper.gui.MainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import org.apache.commons.lang3.Validate;

/**
 * Listens for the menu item version and shows version info dialog.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class VersionInfoListener implements ActionListener {

    /**
     * Used to dispose.
     */
    private final MainWindow main;
    /**
     * Application version.
     */
    private final Version version;

    /**
     * Dedicated constructor.
     *
     * @param main must not be {@code null}
     * @param version must not be {@code null}
     */
    public VersionInfoListener(final MainWindow main, final Version version) {
        super();
        Validate.notNull(main, "Main window must not be null!");
        this.main = main;
        Validate.notNull(version, "Version window must not be null!");
        this.version = version;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        JOptionPane.showMessageDialog(
            main,
            String.format("Version: %s", version.toString()),
            main.getTitle(),
            JOptionPane.PLAIN_MESSAGE);
    }

}
