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
package de.weltraumschaf.minesweeper;

import de.weltraumschaf.commons.swing.MenuBarBuilder;
import de.weltraumschaf.commons.swing.SwingFrame;
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
    /**
     * Id for serialization.
     */
    private static final long serialVersionUID = 1L;
    private final MineField mineField;

    /**
     * Initializes the main window with an title.
     *
     * @param title The window title.
     */
    public MainWindow(final String title, final MineField mineField) {
        super(title);
        this.mineField = mineField;
    }

    @Override
    protected void initMenu() {
        final JMenuBar menubar = MenuBarBuilder.builder()
                .menu("File")
                .item("New")
                .addListener(new Listener())
                .end()
                .item("Version")
                .addListener(new Listener())
                .end()
                .separator()
                .item("Quit")
                .addListener(new Listener())
                .end()
                .end()
                .create();

        setJMenuBar(menubar);
    }

    @Override
    protected void initPanel() {
        final JPanel field = new JPanel();
        field.setLayout(new GridLayout(mineField.getWidth(), mineField.getHeight()));

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

    /**
     * Default listener for playing around.
     *
     * @deprecated Will be removed soon!
     */
    @Deprecated
    private class Listener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            final Object source = e.getSource();
            LOG.log(Level.INFO, String.format("Received event from %s.", source.toString()));
        }
    }

    private static class BoxButtonListener extends MouseAdapter {

        private final MineFieldBox box;
        private boolean opened;
        private boolean flagged;

        public BoxButtonListener(final MineFieldBox box) {
            super();
            this.box = box;
        }

        @Override
        public void mouseClicked(final MouseEvent e) {
            if (opened) {
                return;
            }

            final JButton origin = (JButton) e.getComponent();

            if (SwingUtilities.isRightMouseButton(e)) {
                if (flagged) {
                    origin.setIcon(ImageIcons.CLOSED.getResource());
                    flagged = false;
                } else {
                    origin.setIcon(ImageIcons.FLAG.getResource());
                    flagged = true;
                }
            } else {
                if (flagged) {
                    return;
                }

                if (box.isMine()) {
                    origin.setIcon(ImageIcons.BOMB_EXPLODED.getResource());
                } else {
                    origin.setIcon(determineIcon());
                }

                opened = true;
            }
        }

        private ImageIcon determineIcon() {
            final ImageIcon icon;

            if (box.isMine()) {
                icon = ImageIcons.BOMB.getResource();
            } else {
                switch (box.countMinesInNeighborhood()) {
                    case 0:
                        icon = ImageIcons.BLANK.getResource();
                        break;
                    case 1:
                        icon = ImageIcons.ONE_NEIGHBOR.getResource();
                        break;
                    case 2:
                        icon = ImageIcons.TWO_NEIGHBOR.getResource();
                        break;
                    case 3:
                        icon = ImageIcons.THREE_NEIGHBOR.getResource();
                        break;
                    case 4:
                        icon = ImageIcons.FOUR_NEIGHBOR.getResource();
                        break;
                    case 5:
                        icon = ImageIcons.FIVE_NEIGHBOR.getResource();
                        break;
                    case 6:
                        icon = ImageIcons.SIX_NEIGHBOR.getResource();
                        break;
                    case 7:
                        icon = ImageIcons.SEVEN_NEIGHBOR.getResource();
                        break;
                    case 8:
                        icon = ImageIcons.EIGHT_NEIGHBOR.getResource();
                        break;
                    default:
                        throw new IllegalStateException(String.format("Unsupported neighbor count: %d!",
                                box.countMinesInNeighborhood()));
                }
            }

            return icon;
        }
    }
}
