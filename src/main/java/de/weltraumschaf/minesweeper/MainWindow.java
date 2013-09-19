/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com>
 */
package de.weltraumschaf.minesweeper;

import de.weltraumschaf.commons.swing.MenuBarBuilder;
import de.weltraumschaf.commons.swing.SwingFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuBar;

/**
 * The applications main window.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class MainWindow extends SwingFrame {

    /**
     * Logging facility.
     */
    private static final Logger LOG = Logger.getLogger(MainWindow.class.getName());

    /**
     * Id for serialization.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Initializes the main window with an title.
     *
     * @param title The window title.
     */
    public MainWindow(final String title) {
        super(title);
    }

    @Override
    protected void initMenu() {
        final JMenuBar menubar = MenuBarBuilder.builder()
            .menu("File")
                .item("New")
                    .addListener(new Listener())
                .end()
                .item("Version")
                    .addListener(new Listener())
                .end()
                .separator()
                .item("Quit")
                    .addListener(new Listener())
                .end()
            .end()
            .create();

        setJMenuBar(menubar);
    }

    @Override
    protected void initPanel() {
//        panel.add(null);
    }

    /**
     * Default listener for playing around.
     *
     * @deprecated Will be removed soon!
     */
    @Deprecated private class Listener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            final Object source = e.getSource();
            LOG.log(Level.INFO, String.format("Received event from %s.", source.toString()));
        }

    }
}
