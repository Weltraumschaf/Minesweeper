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

import de.weltraumschaf.minesweeper.GlobalLog;
import de.weltraumschaf.minesweeper.model.FieldBox;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JLabel;
import org.apache.commons.lang3.Validate;

/**
 * Represents a mine filed box button.
 *
 * A button has three states: Closed, open, flagged.
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class FieldBoxButton extends JLabel implements Observer {

    /**
     * Log facility.
     */
    private static final Logger LOG = GlobalLog.getLogger(FieldBoxButton.class);
    /**
     * Zero mines in neighborhood.
     */
    private static final int ZERO_NEIGHBORS = 0;
    /**
     * One mines in neighborhood.
     */
    private static final int ONE_NEIGHBORS = 1;
    /**
     * Two mines in neighborhood.
     */
    private static final int TWO_NEIGHBORS = 2;
    /**
     * Three mines in neighborhood.
     */
    private static final int THREE_NEIGHBORS = 3;
    /**
     * Four mines in neighborhood.
     */
    private static final int FOUR_NEIGHBORS = 4;
    /**
     * Five mines in neighborhood.
     */
    private static final int FIVE_NEIGHBORS = 5;
    /**
     * Six mines in neighborhood.
     */
    private static final int SIX_NEIGHBORS = 6;
    /**
     * Seven mines in neighborhood.
     */
    private static final int SEVEN_NEIGHBORS = 7;
    /**
     * Eight mines in neighborhood.
     */
    private static final int EIGHT_NEIGHBORS = 8;
    /**
     * Shared exception message.
     */
    private static final String BOX_MODEL_NULL_EMESSAGE = "Box model is null! Set box first.";
    /**
     * The data model behind the button.
     */
    private FieldBox box;
    /**
     * State of the button.
     *
     * The button holds an own state additional to {@link #box} to prevent endless loops by observer notification.
     */
    private State state = State.CLOSED;

    /**
     * Dedicated constructor.
     *
     * Initializes the button with closed icon.
     */
    public FieldBoxButton() {
        super(ImageIcons.CLOSED.getResource());
    }

    /**
     * Determine if button is in state.
     *
     * @param s state to check.
     * @return {@code true} if button has passed in state, else {@code false}
     */
    boolean isInState(final State s) {
        return state == s;
    }

    /**
     * Set the state of the button.
     *
     * @param s must not be {@code null}
     */
    void setState(final State s) {
        Validate.notNull(s, "State must not be null!");
        this.state = s;
    }

    /**
     * Get the box model.
     *
     * @return never {@code null}
     */
    public FieldBox getBox() {
        return box;
    }

    /**
     * Set the box model.
     *
     * Must be set before any operation can be performed on the button.
     *
     * @param box must not be {@code null}
     */
    public void setBox(final FieldBox box) {
        Validate.notNull(box, "Box must not be null!");
        this.box = box;

        if (GlobalLog.debug()) {
            setToolTipText(box.toString());
        }
    }

    /**
     * Open the box.
     */
    public void open() {
        Validate.notNull(box, BOX_MODEL_NULL_EMESSAGE);

        if (isOpen()) {
            return;
        }

        state = State.OPEN;

        if (box.isMine()) {
            if (box.getField().isGameOver()) {
                if (box.isFlag()) {
                    setIcon(ImageIcons.BOMB.getResource());
                } else {
                    setIcon(ImageIcons.BOMB_EXPLODED.getResource());
                }
            } else {
                setIcon(ImageIcons.BOMB_EXPLODED.getResource());
                box.getField().setGameOver();
            }
        } else {
            setIcon(determineIcon());

            if (box.countMinesInNeighborhood() == 0) {
                LOG.info("Opened button has 0 mines in neighborhood.");

                for (final FieldBox neighbor : box.getNeighbours()) {
                    LOG.info(String.format("Open neighbor box %s.", neighbor));
                    neighbor.setOpened();
                }
            }
        }

        repaint();
    }

    /**
     * Whether the box is open or not.
     *
     * @return {@code true} if button is open, else {@code false}
     */
    public boolean isOpen() {
        return isInState(State.OPEN);
    }

    /**
     * Close the box.
     *
     * Already opened box can not be closed.
     */
    public void close() {
        if (isInState(State.CLOSED)) {
            return;
        }

        Validate.notNull(box, BOX_MODEL_NULL_EMESSAGE);
        state = State.CLOSED;
        box.setFlag(false);
        setIcon(ImageIcons.CLOSED.getResource());
        repaint();
    }

    /**
     * Flags a button to signal that there may be a mine.
     *
     * Only closed or not flagged buttons can be flagged.
     */
    public void flag() {
        if (isInState(State.FLAG)) {
            return;
        }

        Validate.notNull(box, BOX_MODEL_NULL_EMESSAGE);
        box.setFlag(true);
        setIcon(ImageIcons.FLAG.getResource());
        state = State.FLAG;
        repaint();
    }

    /**
     * Whether the button is flagged.
     *
     * @return {@code true} if the button is in flagged state, else {@code false}
     */
    public boolean isFlag() {
        return isInState(State.FLAG);
    }

    /**
     * Determines the right icon image (either bomb or number for mines in neighberhood).
     *
     * @return never {@code null}
     */
    Icon determineIcon() {
        Validate.notNull(box, BOX_MODEL_NULL_EMESSAGE);
        final Icon icon;

        if (box.isMine()) {
            icon = ImageIcons.BOMB.getResource();
        } else {
            icon = determineNumberIcon();
        }

        return icon;
    }

    /**
     * Determines the number icons for mines in neighborhood.
     *
     * @return never {@code null}
     */
    Icon determineNumberIcon() {
        final Icon icon;

        switch (box.countMinesInNeighborhood()) {
            case ZERO_NEIGHBORS:
                icon = ImageIcons.BLANK.getResource();
                break;
            case ONE_NEIGHBORS:
                icon = ImageIcons.ONE_NEIGHBOR.getResource();
                break;
            case TWO_NEIGHBORS:
                icon = ImageIcons.TWO_NEIGHBOR.getResource();
                break;
            case THREE_NEIGHBORS:
                icon = ImageIcons.THREE_NEIGHBOR.getResource();
                break;
            case FOUR_NEIGHBORS:
                icon = ImageIcons.FOUR_NEIGHBOR.getResource();
                break;
            case FIVE_NEIGHBORS:
                icon = ImageIcons.FIVE_NEIGHBOR.getResource();
                break;
            case SIX_NEIGHBORS:
                icon = ImageIcons.SIX_NEIGHBOR.getResource();
                break;
            case SEVEN_NEIGHBORS:
                icon = ImageIcons.SEVEN_NEIGHBOR.getResource();
                break;
            case EIGHT_NEIGHBORS:
                icon = ImageIcons.EIGHT_NEIGHBOR.getResource();
                break;
            default:
                throw new IllegalStateException(String.format("Unsupported neighbor count: %d!",
                        box.countMinesInNeighborhood()));
        }

        return icon;
    }

    @Override
    public void update(final Observable observable, final Object arg) {
        Validate.notNull(box, BOX_MODEL_NULL_EMESSAGE);
        LOG.info(String.format("Update from observable %s", observable));

        if (box.isOpen()) {
            LOG.info(String.format("Open box %s.", box));
            open();
        }
    }

    /**
     * Resets the button.
     *
     * <ul>
     * <li>set state to closed</li>
     * <li>set icon to closed</li>
     * <li>repaint button</li>
     * </ul>
     */
    void reset() {
        state = State.CLOSED;
        setIcon(ImageIcons.CLOSED.getResource());
        repaint();
    }

    /**
     * Represent the button state.
     */
    static enum State {

        /**
         * Button is closed.
         */
        CLOSED,
        /**
         * Button has flag.
         */
        FLAG,
        /**
         * Button is open.
         */
        OPEN;
    }

}
