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
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

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
//                .item("Version")
//                .addListener(new Listener())
//                .end()
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
                final JButton box = new JButton(ImageIcons.CLOSED.getResource());
                box.addMouseListener(new BoxButtonListener(mineField.getBox(x, y)));
                field.add(box);
            }
        }

        panel.add(field);
        pack();
    }

}