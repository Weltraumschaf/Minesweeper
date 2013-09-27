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
     * Used for OS specific differences.
     */
    private static final OperatingSystem OS = OperatingSystem.determine(System.getProperty("os.name", ""));
    /**
     * Holds the game field.
     */
    private final MineFieldPanel gamePanel;
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
        gamePanel = new MineFieldPanel(mineField.getWidth(), mineField.getHeight());
        gamePanel.init();
    }

    /**
     * Get the game mine field model.
     *
     * @return never {@code null}
     */
    public MineField getMineField() {
        return mineField;
    }

    /**
     * Set the game mine field model.
     *
     * @param mineField must not be {@code null}
     */
    public void setMineField(final MineField mineField) {
        Validate.notNull(mineField, "Mine field must not be null!");
        this.mineField = mineField;
    }

    @Override
    protected void initMenu() {
        final MenuBuilder builder = MenuBarBuilder.builder()
                .menu("File")
                .item("New")
                .addListener(newGameListener)
                .setAccelerator('N')
                .end();

        if (OperatingSystem.MACOSX != OS) {
            builder.item("Version")
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
        LOG.info(String.format("Paint field:%n%s", mineField.toString()));
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
