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
import de.weltraumschaf.minesweeper.GlobalLog;
import de.weltraumschaf.minesweeper.model.MineField;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.JMenuBar;
import org.apache.commons.lang3.Validate;

/**
 * The applications main window.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class MainWindow extends SwingFrame {

    /**
     * Logging facility.
     */
    private static final Logger LOG = GlobalLog.getLogger(MainWindow.class);

    /**
     * Id for serialization.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Model for the game play.
     */
    private MineField mineField;
    /**
     * Listens for version menu item.
     */
    private ActionListener versionInfoListener;
    /**
     * Listens for new game menu item.
     */
    private ActionListener newGameListener;
    /**
     * Listens for quit menu item.
     */
    private ActionListener quitListener;

    /**
     * Initializes the main window with an title.
     *
     * @param title The window title.
     * @param mineField must not be null
     */
    public MainWindow(final String title, final MineField mineField) {
        super(title);
        Validate.notNull(mineField, "Mine field must not be null!");
        this.mineField = mineField;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExitOnCloseWindow(true);
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
                        .addListener(newGameListener)
                    .end()
                    .item("Version")
                        .addListener(versionInfoListener)
                    .end()
                    .separator()
                    .item("Quit")
                        .addListener(quitListener)
                    .end()
                .end()
                .create();

        setJMenuBar(menubar);
    }

    @Override
    public void initPanel() {
        LOG.info(String.format("Pain field:%n%s", mineField.toString()));
        final MineFieldPanel gamePanel = new MineFieldPanel(mineField.getWidth(), mineField.getHeight());
        gamePanel.init();
        // First init so that buttons are available.
        gamePanel.setModels(mineField.getBoxes());
        panel.add(gamePanel);
        pack();
    }

    /**
     * Set listener for version info menu item.
     *
     * @param listener must not be {@code null}
     */
    public void setVersionInfoListener(final ActionListener listener) {
        Validate.notNull(listener, "Listener must not be null!");
        versionInfoListener = listener;
    }

    /**
     * Set listener for new game menu item.
     *
     * @param listener must not be {@code null}
     */
    public void setNewGameListener(final ActionListener listener) {
        Validate.notNull(listener, "Listener must not be null!");
        newGameListener = listener;
    }

    /**
     * Set listener for quit menu item.
     *
     * @param listener must not be {@code null}
     */
    public void setQuitListener(final ActionListener listener) {
        Validate.notNull(listener, "Listener must not be null!");
        quitListener = listener;
    }

}
