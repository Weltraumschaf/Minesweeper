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

package de.weltraumschaf.minesweeper;

import de.weltraumschaf.commons.InvokableAdapter;
import de.weltraumschaf.commons.Version;
import de.weltraumschaf.commons.system.NullExiter;
import de.weltraumschaf.minesweeper.model.MinesweeperSession;
import javax.swing.SwingUtilities;
import org.apache.log4j.Logger;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class SwingApp extends InvokableAdapter implements Runnable {

    /**
     * Log facility.
     */
    private static final Logger LOG = Logger.getLogger(SwingApp.class);
    /**
     * Version information.
     */
    private final Version version;

    public SwingApp(final String[] args, final Version version) {
        super(args);
        this.version = version;
    }

    @Override
    public void execute() throws Exception {
        setExiter(new NullExiter());
        SwingUtilities.invokeLater(this);
    }

    @Override
    public void run() {
        LOG.debug("Create minesweeper session.");
        final MinesweeperSession minesweeper = new MinesweeperSession(version);
        minesweeper.play();
        registerShutdownHook(new Runnable() {

            @Override
            public void run() {
                minesweeper.quit();
            }
        });
    }
}
