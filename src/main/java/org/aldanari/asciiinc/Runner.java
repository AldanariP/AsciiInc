package org.aldanari.asciiinc;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.aldanari.asciiinc.cells.Cell;

import java.util.Objects;

public class Runner extends Application {

    //frame rate constants
    private long lastUpdate = 0;
    public static final int FRAME_RATE = 30;
    private static final long SECOND_TO_NANOS = 1_000_000_000;
    private static final long UPDATE_TIMING = SECOND_TO_NANOS / FRAME_RATE;

    //resolution and size constants
    public static final int FONT_SIZE = 20;
    private static final int WINDOW_WIDTH = 480;
    private static final int WINDOW_HEIGHT = 270;

    @Override
    public void start(Stage stage) {
        GameScreen gameScreen = new GameScreen(FONT_SIZE);
        StackPane root = new StackPane(gameScreen);
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("gamescreen.css")).toExternalForm());

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate > UPDATE_TIMING) {
                    gameScreen.fillWithRandom();
                    lastUpdate = now;
                }
            }
        };
        timer.start();

        GameGrid gg = new GameGrid(20, 20);
        gg.fillWithRandom();
        System.out.println(gg);
        Cell[][] grid = gg.getView(5, 5, 10, 10);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.print(grid[i][j].toChar());
            }
            System.out.println();
        }

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}