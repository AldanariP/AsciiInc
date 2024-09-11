package org.aldanari.asciiinc.cells;

public class MiningCell extends Cell {

	private final int x;
	private final int y;
	private final char skin;

	public MiningCell(int x, int y) {
		this.x = x;
		this.y = y;
		this.skin = '▒';
	}

	@Override
	public Character toChar() {
		return this.skin;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
