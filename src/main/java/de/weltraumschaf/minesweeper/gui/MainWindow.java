/*
 * LICENSE
 *
 * "THE BEER-WARE LICENSE" (Revision 43):
 * "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com> wrote this file.
 * As long as you retain this notice you can do whatever you want with
 * this stuff. If we meet some day, and you think this stuff is worth it,
 * you can buy me a non alcohol free beer in return.
 *
 * Copyright (C) 2012 "Sven Strittmatter" <weltraumschaf(at)googlemail(dot)com>
 */
package de.weltraumschaf.minesweeper.gui;

import de.weltraumschaf.commons.swing.MenuBarBuilder;
import de.weltraumschaf.commons.swing.SwingFrame;
import de.weltraumschaf.minesweeper.model.MineField;
import de.weltraumschaf.minesweeper.model.MineFieldBox;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 * The applications main window.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class MainWindow extends SwingFrame {

    /**
     * Logging facility.
     */
    private static final Logger LOG = Logger.getLogger(MainWindow.class.getName());

    static {
        LOG.setLevel(Level.ALL);
    }
    /**
     * Id for serialization.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Model for the game play.
     */
    private MineField mineField;

    /**
     * Initializes the main window with an title.
     *
     * @param title The window title.
     */
    public MainWindow(final String title, final MineField mineField) {
        super(title);
        this.mineField = mineField;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public MineField getMineField() {
        return mineField;
    }

    public void setMineField(final MineField mineField) {
        this.mineField = mineField;
    }

    @Override
    protected void initMenu() {
        final JMenuBar menubar = MenuBarBuilder.builder()
                .menu("File")
                .item("New")
                .addListener(new NewGameListener(this))
                .end()
                .item("Version")
                .addListener(new VersionInfoListener(this))
                .end()
                .separator()
                .item("Quit")
                .addListener(new QuitListener(this))
                .end()
                .end()
                .create();

        setJMenuBar(menubar);
    }

    @Override
    protected void initPanel() {
        panel.removeAll();
        final JPanel field = new JPanel();
        field.setLayout(new GridLayout(mineField.getWidth(), mineField.getHeight()));
        LOG.info(String.format("Pain field:%n%s", mineField.toString()));

        for (int x = 0; x < mineField.getWidth(); ++x) {
            for (int y = 0; y < mineField.getHeight(); ++y) {
                final MineFieldBox box = mineField.getBox(x, y);
                final FieldBoxButton button = new FieldBoxButton(box);
                box.addObserver(button);
                button.addMouseListener(new FieldBoxButtonListener());
                field.add(button);
            }
        }

        panel.add(field);
        pack();
    }

}
