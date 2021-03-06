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
import de.weltraumschaf.commons.swing.MenuBuilder;
import de.weltraumschaf.commons.swing.SwingFrame;
import de.weltraumschaf.commons.system.OperatingSystem;
import de.weltraumschaf.minesweeper.model.MineField;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

/**
 * The applications main window.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public final class MainWindow extends SwingFrame {

    /**
     * Logging facility.
     */
    private static final Logger LOG = Logger.getLogger(MainWindow.class);
    /**
     * Id for serialization.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Used for OS specific differences.
     */
    private static final OperatingSystem OS = OperatingSystem.determine(System.getProperty("os.name", ""));
    /**
     * UI status bar.
     */
    private final StatusBar statusbar = new StatusBar();
    /**
     * Holds the game field.
     */
    private MineFieldPanel gamePanel;
    /**
     * Model for the game play.
     */
    private MineField mineField;
    /**
     * Listens for version menu item.
     */
    private ActionListener versionInfoListener;
    /**
     * Listener for size menu items.
     */
    private ActionListener resizeFieldListener;
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
     */
    public MainWindow(final String title) {
        super(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExitOnCloseWindow(true);
        gamePanel = new MineFieldPanel(MineField.DEFAULT_WIDTH, MineField.DEFAULT_HEIGHT, this);
        gamePanel.init();
    }

    /**
     * Get the UI status bar.
     *
     * @return never {@code null}
     */
    public StatusBar getStatusbar() {
        return statusbar;
    }

    /**
     * Set the game mine field model.
     *
     * @param mf must not be {@code null}
     */
    public void setMineField(final MineField mf) {
        Validate.notNull(mf, "Mine field must not be null!");
        LOG.debug(String.format("Set field:%n%s", mf.toString()));

        if (shouldReinitializeGamePanel(mf)) {
            LOG.debug("Reinitialize game panel.");
            gamePanel = new MineFieldPanel(mf.getWidth(), mf.getHeight(), this);
            gamePanel.changeSize(mf.getWidth(), mf.getHeight());
            gamePanel.init();
            gamePanel.repaint();
        }

        mineField = mf;
        // First init so that buttons are available.
        gamePanel.setModels(mineField.getBoxes());
    }

    @Override
    protected void initMenu() {
        final MenuBuilder builder = MenuBarBuilder.builder()
                .menu("File")
                .item("New")
                .addListener(newGameListener)
                .setAccelerator('N')
                .end()
                .separator()
                .item(Size.SMALL.getActionCommand())
                .addListener(resizeFieldListener)
                .setAccelerator('1')
                .end()
                .item(Size.MEDIUM.getActionCommand())
                .addListener(resizeFieldListener)
                .setAccelerator('2')
                .end()
                .item(Size.LARGE.getActionCommand())
                .addListener(resizeFieldListener)
                .setAccelerator('3')
                .end();

        if (OperatingSystem.MACOSX != OS) {
            builder.separator()
                    .item("Version")
                    .addListener(versionInfoListener)
                    .end()
                    .separator()
                    .item("Quit")
                    .addListener(quitListener)
                    .setAccelerator('Q')
                    .end();
        }

        setJMenuBar(builder.end().create());
    }

    @Override
    public void initPanel() {
        LOG.debug("Init panel of main window.");
        LOG.debug("Add game panel to main window.");
        panel.add(gamePanel);
        LOG.debug("Add status bar to main window.");
        getContentPane().add(statusbar, BorderLayout.SOUTH);
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

    /**
     * Set listener for field size menu items.
     *
     * @param listener must not be {@code null}
     */
    public void setResizeFieldListener(final ActionListener listener) {
        Validate.notNull(listener, "Listener must not be null!");
        resizeFieldListener = listener;
    }

    /**
     * Determines if the game panel should be initialized.
     *
     * This is the case if the passed in filed has different dimensions than {@link #mineField}.
     *
     * @param mf must not be {@code null}
     * @return {@code true} if game panel should reinitialize, else {@code false}
     */
    private boolean shouldReinitializeGamePanel(final MineField mf) {
        Validate.notNull(mf, "Passed in mine filed must not be null!");
        return mineField != null && (mf.getWidth() != mineField.getWidth() || mf.getHeight() != mineField.getHeight());
    }

}
