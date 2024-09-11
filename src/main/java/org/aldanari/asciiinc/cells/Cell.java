package org.aldanari.asciiinc.cells;

/***
 * The interface all Cell-like classes must implement for it to be accepted in a GameGrid.
 */
public abstract class Cell {
	public abstract Character toChar();

	public boolean is(Class<? extends Cell> clazz) {
		return this.getClass() == clazz;
	}
}
