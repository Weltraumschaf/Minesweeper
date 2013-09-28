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

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class LabeledValue extends JPanel {
    private static final int ROWS = 1;
    private static final int COLS = 2;

    private final JLabel label = new JLabel();
    private final JLabel value = new JLabel();

    public LabeledValue(final String labelText) {
        super(new GridLayout(ROWS, COLS));
        label.setText(labelText);
        add(label);
        add(value);
    }

    public void setValue(final String valueText) {
        value.setText(valueText);
    }

}
