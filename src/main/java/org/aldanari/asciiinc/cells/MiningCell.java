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

	// used for debuggin, not intended to have different skin on mining cell
	public MiningCell(int x, int y, char altSkin) {
		this.x = x;
		this.y = y;
		this.skin = altSkin;
	}

	@Override
	public Character toChar() {
		return this.skin;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
}
