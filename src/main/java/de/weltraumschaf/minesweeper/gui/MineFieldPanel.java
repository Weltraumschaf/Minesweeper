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
import de.weltraumschaf.minesweeper.control.FieldBoxListeners;
import de.weltraumschaf.minesweeper.model.FieldBox;
import java.awt.GridLayout;
import javax.swing.JPanel;
import org.apache.commons.lang3.Validate;

/**
 * Paints the game field.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MineFieldPanel extends JPanel {

    /**
     * Holds the game field buttons in a matrix.
     */
    private final Matrix<FieldBoxButton> fieldButtons;
    /**
     * Count of buttons in x-axis.
     */
    private final int width;
    /**
     * Count of buttons in y-axis.
     */
    private final int height;
    /**
     * Whether the was initialized.
     */
    private boolean initialized;

    /**
     * Dedicated constructor.
     *
     * @param width must not be less than 1
     * @param height must not be less than 1
     */
    public MineFieldPanel(int width, int height) {
        super(new GridLayout(width, height));
        this.fieldButtons = new Matrix<FieldBoxButton>(FieldBoxButton.class, width, height);
        this.width = width;
        this.height = height;
    }

    /**
     * Initializes the panel with default buttons with attached click listeners and add them to the panel.
     *
     * Executes only one time per instance.
     */
    public void init() {
        if (initialized) {
            return;
        }

        fieldButtons.initWithObjects();

        for (final FieldBoxButton btn : fieldButtons.getAll()) {
            btn.addMouseListener(FieldBoxListeners.createClickListener());
            add(btn);
        }

        initialized = true;
    }

    /**
     * Set the field box models.
     *
     * @param boxes must not be {@code null} and must have same size as {@link #fieldButtons}
     */
    public void setModels(final Matrix<FieldBox> boxes) {
        Validate.notNull(boxes, "Boxes must not be null!");
        Validate.isTrue(boxes.size() == fieldButtons.size(), "Size of buttons and boxes matrix must be equal!");

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                final FieldBox box = boxes.get(x, y);
                final FieldBoxButton btn = fieldButtons.get(x, y);
                box.addObserver(btn);
                btn.setBox(box);
                btn.reset();
            }
        }
    }

}
