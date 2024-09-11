package org.aldanari.asciiinc.cells;

public class CharCell extends Cell {

	private final Character value;

	public CharCell(Character value) {
		this.value = value;
	}

	@Override
	public Character toChar() {
		return this.value;
	}
}
