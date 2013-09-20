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

import de.weltraumschaf.minesweeper.Matrix;
import javax.swing.JPanel;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MineFieldPanel extends JPanel {

    private final int width;
    private final int height;
    private final Matrix<FieldBoxButton> fieldButtons;

    public MineFieldPanel(int width, int height) {
        this.width = width;
        this.height = height;
        this.fieldButtons = new Matrix<FieldBoxButton>(FieldBoxButton.class, width, height);
    }

}
