package org.aldanari.asciiinc.cells;

public class CharCell implements Cell {

	private final Character value;

	public CharCell(Character value) {
		this.value = value;
	}

	@Override
	public Character toChar() {
		return this.value;
	}
}
