package org.aldanari.asciiinc;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;


public class Runner extends Application {

	//frame rate constants
	private long lastUpdate = 0;
	public static final int FRAME_RATE = 60;
	private static final long SECOND_TO_NANOS = 1_000_000_000;
	private static final long UPDATE_TIMING = SECOND_TO_NANOS / FRAME_RATE;

	//resolution and size constants
	public static final int FONT_SIZE = 20;
	private static final int WINDOW_WIDTH = 480;
	private static final int WINDOW_HEIGHT = 270;

	//Game grid properties
	public static final int GRID_HEIGTH = 40;
	public static final int GRID_WIDTH = 80;
	public static final float GRID_DENSITY = 0.003F;
	public static final int MINING_CROP_SIZE = 2;
	public static final float MINING_CROP_ENTROPY = 15F;
	public static final long GRID_SEED = 1L;

	@Override
	public void start(Stage stage) {
		// Init Grid and generate terrain
		GameGrid gameGrid = new GameGrid(GRID_WIDTH, GRID_HEIGTH);
		GridGenerator gridGenerator = new GridGenerator(gameGrid, GRID_SEED);
		gridGenerator.placeMiningCell(GRID_DENSITY, MINING_CROP_SIZE, MINING_CROP_ENTROPY);

		// Init Screen
		GameScreen gameScreen = new GameScreen(FONT_SIZE);
		gameScreen.lookAt(gameGrid);

		// Init Scene
		StackPane root = new StackPane(gameScreen);
		Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
		scene.getStylesheets().add(Objects.requireNonNull(this.getClass().getResource("gamescreen.css")).toExternalForm());

		// Init controls
		InputManager inputManager = new InputManager(gameScreen);
		scene.setOnKeyPressed(keyEvent -> inputManager.addKeyPressed(keyEvent.getCode()));
		scene.setOnKeyReleased(keyEvent -> inputManager.removeKeyPressed(keyEvent.getCode()));

		// Setup graphic loop
		AnimationTimer graphicTimer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (now - Runner.this.lastUpdate > UPDATE_TIMING) {
					gameScreen.updateView();
					Runner.this.lastUpdate = now;
				}
			}
		};
        graphicTimer.start();

		// Setup and show stage
		stage.setTitle("AsciiInc");
		stage.resizableProperty().setValue(false);
		stage.requestFocus();
		stage.setOnCloseRequest(_ -> inputManager.shutdown());
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}