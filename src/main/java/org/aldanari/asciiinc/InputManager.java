package org.aldanari.asciiinc;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InputManager {

	private final ConcurrentHashMap.KeySetView<KeyCode, Boolean> pressedKeys = ConcurrentHashMap.newKeySet();
	private final ScheduledExecutorService scheduler;
	private final GameScreen screen;

	public InputManager(GameScreen screen) {
		super();
		this.screen = screen;

		int schedulerPeriod = 1000 / Config.getConfig().getFrameRate();

		this.scheduler = Executors.newSingleThreadScheduledExecutor();
		this.scheduler.scheduleAtFixedRate(this::handle, 0, schedulerPeriod, TimeUnit.MILLISECONDS);
	}

	public void addKeyPressed(KeyCode keyCode) {
		this.pressedKeys.add(keyCode);
	}

	public void removeKeyPressed(KeyCode keyCode) {
		this.pressedKeys.remove(keyCode);
	}

	public void shutdown() {
		this.scheduler.shutdown();
		try {
			if (!this.scheduler.awaitTermination(800, TimeUnit.MILLISECONDS)) {
				this.scheduler.shutdownNow();
			}
		} catch (InterruptedException e) {
			this.scheduler.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}

	public void handle() {
		Set<KeyCode> snapshot = new HashSet<>(this.pressedKeys);
		snapshot.forEach(keyCode -> Platform.runLater(() -> this.screen.handleKeyPress(keyCode)));
	}
}
