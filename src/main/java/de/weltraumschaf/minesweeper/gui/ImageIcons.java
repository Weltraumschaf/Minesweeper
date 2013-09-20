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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.ImageIcon;
import org.apache.commons.lang3.Validate;

/**
 * ImageIcons for mine field boxes.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum ImageIcons {

    /**
     * Blank open box.
     *
     * Used for opened boxes with 0 mines in neighborhood.
     */
    BLANK("blank.png"),
    /**
     * Flagged box.
     *
     * Signals that the user thinks there is a mine.
     */
    FLAG("flag.png"),
    /**
     * Default image for a field box.
     */
    CLOSED("closed.png"),
    /**
     * Image for a opened mine.
     */
    BOMB("bomb.png"),
    /**
     * Image for a opened and exploded mine.
     */
    BOMB_EXPLODED("bomb_exploded.png"),
    /**
     * Opened field box with one mine in neighborhood.
     */
    ONE_NEIGHBOR("one_neighbor.png"),
    /**
     * Opened field box with two mine in neighborhood.
     */
    TWO_NEIGHBOR("two_neighbor.png"),
    /**
     * Opened field box with three mine in neighborhood.
     */
    THREE_NEIGHBOR("three_neighbor.png"),
    /**
     * Opened field box with four mine in neighborhood.
     */
    FOUR_NEIGHBOR("four_neighbor.png"),
    /**
     * Opened field box with five mine in neighborhood.
     */
    FIVE_NEIGHBOR("five_neighbor.png"),
    /**
     * Opened field box with six mine in neighborhood.
     */
    SIX_NEIGHBOR("six_neighbor.png"),
    /**
     * Opened field box with seven mine in neighborhood.
     */
    SEVEN_NEIGHBOR("seven_neighbor.png"),
    /**
     * Opened field box with eight mine in neighborhood.
     */
    EIGHT_NEIGHBOR("eight_neighbor.png");
    /**
     * Base package where icon binaries are.
     */
    private static final String BASE_PACKAGE = "de/weltraumschaf/minesweeper/";
    /**
     * Cache to pre load each image binary one time.
     */
    private static final Map<ImageIcons, ImageIcon> CACHE = new ConcurrentHashMap<ImageIcons, ImageIcon>();
    /**
     * Base name of image.
     */
    private final String baseName;

    static {
        // Initialize the cache.
        for (final ImageIcons icn : values()) {
            CACHE.put(icn, icn.getResource());
        }
    }

    /**
     * Dedicated constructor.
     *
     * @param baseName must not be {@code null} or empty
     */
    private ImageIcons(final String baseName) {
        Validate.notEmpty(baseName, "Base name must not be null or empry!");
        this.baseName = baseName;
    }

    /**
     * Get image resource.
     *
     * @return never {@code null}, same instance
     */
    public ImageIcon getResource() {
        if (CACHE.containsKey(this)) {
            return CACHE.get(this);
        }

        return new ImageIcon(getClass().getClassLoader().getResource(BASE_PACKAGE + baseName));
    }

}
