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

import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import org.apache.commons.lang3.Validate;

/**
 * UI element with a fixed label and a changeable value.
 *
 * It is a combination of two {@link JLabel labels} in a grid layout.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
class LabeledValue extends JLabel {

    /**
     * Fixed label text.
     */
    private final String labelText;

    /**
     * Dedicated constructor.
     *
     * Initializes the fixed label text.
     *
     * @param labelText must not be {@code null} or empty
     */
    public LabeledValue(final String labelText) {
        super();
        Validate.notEmpty(labelText, "Label text must not be null or empty!");
        this.labelText = labelText;
        setText(renderText(""));
    }

    /**
     * Sets the changeable value text.
     *
     * @param valueText must not be {@code null}
     */
    public void setValue(final String valueText) {
        Validate.notNull(valueText, "Value text must not be null!");
        setText(renderText(valueText));
    }

    /**
     * Renders the whole label string.
     *
     * @param valueText volatile value of label
     * @return never {@code null}
     */
    private String renderText(final String valueText) {
        return String.format("%s: %s", labelText, valueText);
    }

}
