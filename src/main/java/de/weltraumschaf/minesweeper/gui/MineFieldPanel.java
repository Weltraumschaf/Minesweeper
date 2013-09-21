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
import java.util.List;
import javax.swing.JPanel;
import org.apache.commons.lang3.Validate;

/**
 * Paints the game field.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MineFieldPanel extends JPanel {

    private final Matrix<FieldBoxButton> fieldButtons;

    public MineFieldPanel(int width, int height) {
        super(new GridLayout(width, height));
        this.fieldButtons = new Matrix<FieldBoxButton>(FieldBoxButton.class, width, height);
    }

    public void init() {
        fieldButtons.initWithObjects();

        for (final FieldBoxButton btn : fieldButtons.getAll()) {
            btn.addMouseListener(FieldBoxListeners.createClickListener());
            add(btn);
        }
    }

    public void setModels(final List<FieldBox> boxes) {
        Validate.isTrue(boxes.size() == fieldButtons.size(), "Size of buttons and boxes matrix must be equal!");

        int i = 0;
        for (final FieldBoxButton btn : fieldButtons.getAll()) {
            final FieldBox box = boxes.get(i);
            box.addObserver(btn);
            btn.setBox(box);
            ++i;
        }
    }

}
