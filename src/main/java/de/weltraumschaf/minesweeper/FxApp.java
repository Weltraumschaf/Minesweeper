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

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Sven Strittmatter <weltraumschaf@googlemail.com>
 */
public class FxApp extends Application {

    @Override
    public void start(final Stage stage) throws Exception {
        stage.setTitle("Minesweeper");
        final GridPane grid = new GridPane();
        grid.add(new Text("Welcome"), 0, 0, 2, 1);
        final Scene scene = new Scene(grid);
        stage.setScene(scene);
        stage.show();
    }

}
