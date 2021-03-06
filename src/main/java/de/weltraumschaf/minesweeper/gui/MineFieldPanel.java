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
import org.apache.log4j.Logger;

/**
 * Paints the game field.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class MineFieldPanel extends JPanel {

    /**
     * Log facility.
     */
    private static final Logger LOG = Logger.getLogger(MineFieldPanel.class);
    /**
     * Holds the game field buttons in a matrix.
     */
    private Matrix<FieldBoxButton> fieldButtons;
    /**
     * Count of buttons in x-axis.
     */
    private int width;
    /**
     * Count of buttons in y-axis.
     */
    private int height;
    /**
     * Frame to which the listener was added.
     */
    private final MainWindow main;
    /**
     * Whether the was initialized.
     */
    private boolean initialized;

    /**
     * Dedicated constructor.
     *
     * @param width must not be less than 1
     * @param height must not be less than 1
     * @param main must not be {@code null}
     */
    public MineFieldPanel(int width, int height, final MainWindow main) {
        super(new GridLayout(width, height));
        this.fieldButtons = new Matrix<FieldBoxButton>(FieldBoxButton.class, width, height);
        this.width = width;
        this.height = height;
        Validate.notNull(main, "Main must not be null!");
        this.main = main;
    }

    /**
     * Initializes the panel with default buttons with attached click listeners and add them to the panel.
     *
     * Executes only one time per instance.
     */
    public void init() {
        LOG.debug("Initialize mine field panel.");

        if (initialized) {
            LOG.debug("Mine field panel already intialized.");
            return;
        }

        fieldButtons.initWithObjects();

        for (final FieldBoxButton btn : fieldButtons.getAll()) {
            btn.addMouseListener(FieldBoxListeners.createClickListener(main));
            add(btn);
        }

        initialized = true;
    }

    public void changeSize(final int width, final int height) {
        LOG.debug(String.format("Resize mine field panel to %dx%d", width, height));
        this.width = width;
        this.height = height;
        this.fieldButtons = new Matrix<FieldBoxButton>(FieldBoxButton.class, width, height);
        this.initialized = false;
    }

    /**
     * Set the field box models.
     *
     * @param boxes must not be {@code null} and must have same size as {@link #fieldButtons}
     */
    public void setModels(final Matrix<FieldBox> boxes) {
        Validate.notNull(boxes, "Boxes must not be null!");
        Validate.isTrue(boxes.size() == fieldButtons.size(),
                String.format("Size of buttons (%d) and boxes (%d) matrix must be equal!",
                boxes.size(), fieldButtons.size()));

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
