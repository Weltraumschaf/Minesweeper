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

import org.apache.log4j.Logger;
import org.apache.log4j.Level;

/**
 * Holds global log level.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class GlobalLog {

    /**
     * Hidden for pure static class.
     */
    private GlobalLog() {
        super();
    }

    /**
     * Whether application debugging is enabled.
     *
     * @return {@code true} if current level is ALL, else {@code false}
     */
    public static boolean debug() {
        return Level.DEBUG.equals(getLogger(GlobalLog.class).getLevel());
    }

    /**
     * Returns a logger for a class set with global level.
     *
     * @param <T> type of class
     * @param type must not be {@code null}
     * @return never {@code null}
     */
    public static <T> Logger getLogger(final Class<T> type) {
        return Logger.getLogger(type.getName());
    }

}
