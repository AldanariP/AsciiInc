package org.aldanari.asciiinc;

import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameScreen extends TextArea {

	private int posX;
	private int posY;

	// ALWAYS use getters to access the size because the TextArea is zero sized when constructed
	private Integer xSize;
	private Integer ySize;

	private GameGrid gameGrid;

	public GameScreen(int fontSize) {
		super();

		this.setEditable(false);
		this.setWrapText(false);
		this.setFont(Font.font("Monospaced", fontSize));
		this.setId("GameScreen");

		this.xSize = null;
		this.ySize = null;

		this.posX = 0;
		this.posY = 0;

		this.gameGrid = null;
	}

	public int getXSize() {
		if (this.xSize == null) {
			this.xSize = this.getCharsPerLine();
		}
		return this.xSize;
	}

	public int getYSize() {
		if (this.ySize == null) {
			this.ySize = this.getLinePerHeigth();
		}
		return this.ySize;
	}

	private int getCharsPerLine() {
		double width = this.getWidth();

		Text text = new Text("W");
		text.setFont(this.getFont());
		double charWidth = text.getLayoutBounds().getWidth();

		return (int) (width / charWidth);
	}

	private int getLinePerHeigth() {
		double heigth = this.getHeight();

		Text text = new Text("W");
		text.setFont(this.getFont());
		double charHeight = text.getLayoutBounds().getHeight();

		return (int) (heigth / charHeight);
	}

	public void updateView() {
		System.out.println(this.posX + " " + this.posY);
		this.setText(this.gameGrid.getViewAsString(this.posX, this.posY, this.getXSize(), this.getYSize()));
	}

	public void lookAt(GameGrid gameGrid) {
		this.gameGrid = gameGrid;
	}

	public void notifyPosChange(KeyCode keyCode) {
		switch (keyCode) {
			case UP -> this.posY++;
			case DOWN -> this.posY--;
			case RIGHT -> this.posX++;
			case LEFT -> this.posX--;
			default -> {/* Do Nothing */}
		}
	}

	public void fillWithRandom() {
		int xRange = this.getXSize();
		int yRange = this.getYSize();

		StringBuilder text = new StringBuilder();
		for (int i = 0; i < yRange; i++) {
			for (int j = 0; j < xRange; j++) {
				double f = Math.random() / Math.nextDown(1f);
				int randomCode = (int) (32 * (1f - f) + 125 * f);
				text.append((char) randomCode);
			}
			if (i < yRange - 1) {
				text.append("\n");
			}
		}
		this.setText(text.toString());
	}
}
