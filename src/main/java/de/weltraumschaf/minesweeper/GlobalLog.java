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

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.Validate;

/**
 * Holds global log level.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class GlobalLog {

    /**
     * Current level.
     */
    private static final Level LEVEL = Level.ALL;

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
        return Level.ALL.equals(getLevel());
    }

    /**
     * Get the level.
     *
     * @return never {@code null}
     */
    public static Level getLevel() {
        return LEVEL;
    }

    /**
     * Set log level on a logger.
     *
     * @param logger must not be {@code null}
     * @return the passed in logger
     */
    public static Logger setLevel(final Logger logger) {
        Validate.notNull(logger, "Logger must not be null!");
        logger.setLevel(getLevel());
        return logger;
    }

    /**
     * Returns a logger for a class set with global level.
     *
     * @param <T> type of class
     * @param type must not be {@code null}
     * @return never {@code null}
     */
    public static <T> Logger getLogger(final Class<T> type) {
        return setLevel(Logger.getLogger(type.getName()));
    }

}
