package org.aldanari.asciiinc;

import javafx.event.EventHandler;

import javafx.scene.input.KeyEvent;

public class InputManager implements EventHandler<KeyEvent> {

	private GameScreen screen;

	public InputManager() {
		super();
		this.screen = null;
	}

	public void notify(GameScreen screen) {
		this.screen = screen;
	}

	@Override
	public void handle(KeyEvent keyEvent) {
		if (this.screen == null) return;
		System.out.println(keyEvent.getCode());
		this.screen.notifyPosChange(keyEvent.getCode());
	}
}
