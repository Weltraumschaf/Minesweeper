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

import com.google.common.collect.Maps;
import java.util.Map;
import org.apache.commons.lang3.Validate;

/**
 * Defines available mine field sizes.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum Size {

    /**
     * Small mine field.
     */
    SMALL("Small", 8, 8),
    /**
     * Medium mine field.
     */
    MEDIUM("Medium", 16, 16),
    /**
     * Large mine field.
     */
    LARGE("Large", 32, 32);
    /**
     * Used to look up size for action name.
     */
    private static final Map<String, Size> LOOKUP = Maps.newHashMap();
    static {
        for (final Size s : values()) {
            LOOKUP.put(s.getActionCommand(), s);
        }
    }
    /**
     * Width of mine field.
     */
    private final int width;
    /**
     * Height of mine filed.
     */
    private final int height;
    /**
     * Action command name.
     */
    private final String actionCommand;

    /**
     * Dedicated constructor.
     *
     * @param width must not be less than 1
     * @param height must not be less than 1
     * @param actionCommand must not be {@code null} or empty
     */
    private Size(final String actionCommand, final int width, final int height) {
        Validate.notEmpty(actionCommand, "Action commmand must not be null or empty!");
        this.actionCommand = actionCommand;
        Validate.isTrue(width > 0, "Width must not be less than 0!");
        this.width = width;
        Validate.isTrue(height > 0, "Height must not be less than 0!");
        this.height = height;
    }

    /**
     * Returns size according to its action command.
     *
     * Throws {@link IllegalArgumentException} for unknown action command.
     *
     * @param actionCommand must not be {@code null} or empty
     * @return never {@code null}
     */
    public static Size getForActioncommand(final String actionCommand) {
        Validate.notEmpty(actionCommand, "Action command must nbot be null or empty!");

        if (!LOOKUP.containsKey(actionCommand)) {
            throw new IllegalArgumentException(String.format("Unknown action command '%s'!", actionCommand));
        }

        return LOOKUP.get(actionCommand);
    }

    /**
     * Get mine field width.
     *
     * @return greater 0
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get mine field height.
     *
     * @return greater 0
     */
    public int getHeight() {
        return height;
    }

    /**
     * Action command name.
     *
     * @return never empty or {@code null}
     */
    public String getActionCommand() {
        return actionCommand;
    }

}
