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
import de.weltraumschaf.minesweeper.model.Minesweeper;
import de.weltraumschaf.minesweeper.gui.MainWindow;
import java.awt.event.ActionListener;

/**
 * Provides menu item action listeners.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class MenuItemListeners {

    /**
     * Hidden because pure static factory.
     */
    private MenuItemListeners() {
        super();
    }

    /**
     * Creates new game listener.
     *
     * @param main must not be {@code null}
     * @param session must not be {@code null}
     * @return always new instance, never {@code null}
     */
    public static ActionListener createNewGameListener(final MainWindow main, final Minesweeper session) {
        return new NewGameListener(main, session);
    }

    /**
     * Creates new version listener.
     *
     * @param main must not be {@code null}
     * @param version must not be {@code null}
     * @return always new instance, never {@code null}
     */
    public static ActionListener createVersionListener(final MainWindow main, final Version version) {
        return new VersionInfoListener(main, version);
    }

    /**
     * Creates new quit listener.
     *
     * @param main must not be {@code null}
     * @return always new instance, never {@code null}
     */
    public static ActionListener createQuitListener(final MainWindow main) {
        return new QuitListener(main);
    }

}
