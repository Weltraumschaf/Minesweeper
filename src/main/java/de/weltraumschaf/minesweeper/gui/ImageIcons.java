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

/**
 * ImageIcons for mine field boxes.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public enum ImageIcons {

    BLANK("blank.png"),
    FLAG("flag.png"),
    CLOSED("closed.png"),
    BOMB("bomb.png"),
    BOMB_EXPLODED("bomb_exploded.png"),
    ONE_NEIGHBOR("one_neighbor.png"),
    TWO_NEIGHBOR("two_neighbor.png"),
    THREE_NEIGHBOR("three_neighbor.png"),
    FOUR_NEIGHBOR("four_neighbor.png"),
    FIVE_NEIGHBOR("five_neighbor.png"),
    SIX_NEIGHBOR("six_neighbor.png"),
    SEVEN_NEIGHBOR("seven_neighbor.png"),
    EIGHT_NEIGHBOR("eight_neighbor.png");

    private static final String BASE_PACKAGE = "de/weltraumschaf/minesweeper/";
    private static final Map<ImageIcons, ImageIcon> CACHE = new ConcurrentHashMap<ImageIcons, ImageIcon>();
    private final String baseName;

    static {
        for (final ImageIcons icn : values()) {
            CACHE.put(icn, icn.getResource());
        }
    }

    private ImageIcons(final String baseName) {
        this.baseName = baseName;
    }

    public ImageIcon getResource() {
        if (CACHE.containsKey(this)) {
            return CACHE.get(this);
        }

        return new ImageIcon(getClass().getClassLoader().getResource(BASE_PACKAGE + baseName));
    }

}
