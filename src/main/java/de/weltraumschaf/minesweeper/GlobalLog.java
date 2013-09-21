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
     * Whether application debugging is enabled.
     */
    private static final boolean DEBUG = true;

    /**
     * Hidden for pure static class.
     */
    private GlobalLog() {
        super();
    }

    /**
     * Whether application debugging is enabled.
     *
     * @return {2code true} if debugging is enabled, else {@code false}
     */
    public static boolean debug() {
        return DEBUG;
    }

    /**
     * Get the level.
     *
     * @return never {@code null}
     */
    public static Level getLevel() {
        return Level.ALL;
    }

    /**
     * Set log level on a logger.
     *
     * @param logger must not be {@code null}
     */
    public static Logger setLevel(final Logger logger) {
        Validate.notNull(logger, "Logger must not be null!");
        logger.setLevel(getLevel());
        return logger;
    }

    public static <T> Logger getLogger(final Class<T> type) {
        return setLevel(Logger.getLogger(type.getName()));
    }
}
