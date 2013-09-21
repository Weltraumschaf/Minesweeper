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

    private final Matrix<FieldBoxButton> fieldButtons;
    private final int width;
    private final int height;

    public MineFieldPanel(int width, int height) {
        super(new GridLayout(width, height));
        this.fieldButtons = new Matrix<FieldBoxButton>(FieldBoxButton.class, width, height);
        this.width = width;
        this.height = height;
    }

    public void init() {
        fieldButtons.initWithObjects();

        for (final FieldBoxButton btn : fieldButtons.getAll()) {
            btn.addMouseListener(FieldBoxListeners.createClickListener());
            add(btn);
        }
    }

    public void setModels(final Matrix<FieldBox> boxes) {
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
