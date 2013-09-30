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
package de.weltraumschaf.minesweeper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * http://stackoverflow.com/questions/13366780/how-to-add-real-time-date-and-time-into-a-jframe-component-e-g-status-bar
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class TimerThread extends Thread {
    private static final long SLEEP = 5000L;

    private final JLabel timeLabel;
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
    protected boolean isRunning;

    public TimerThread(final JLabel timeLabel) {
        super();
        this.timeLabel = timeLabel;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    final Calendar currentCalendar = Calendar.getInstance();
                    final Date currentTime = currentCalendar.getTime();
                    timeLabel.setText(timeFormat.format(currentTime));
                }
            });

            try {
                Thread.sleep(SLEEP);
            } catch (final InterruptedException e) {
                // Ignore sleep errors.
            }
        }
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

}
