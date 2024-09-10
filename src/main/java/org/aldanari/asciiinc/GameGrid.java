package org.aldanari.asciiinc;

import org.aldanari.asciiinc.cells.Cell;
import org.aldanari.asciiinc.cells.CharCell;

import java.util.Arrays;

public class GameGrid {
	private final Cell[][] grid;

	public GameGrid(int width, int height) {
		this.grid = new Cell[width][height];
	}

	public Cell[][] getGrid() {
		return grid;
	}

	public Cell[][] getView(int x, int y, int width, int height) {
		Cell[][] newGrid = new Cell[width][height];
		for (int i = 0; i < height; i++) {
			newGrid[i] = Arrays.copyOfRange(this.grid[y + i], x, x + width);
		}
		return newGrid;
	}

	public void fillWithRandom() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				double f = Math.random() / Math.nextDown(1f);
				int randomCode = (int) (32 * (1f - f) + 125 * f);
				this.grid[i][j] = new CharCell((char) randomCode);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Cell[] cells : grid) {
			for (Cell cell : cells) {
				sb.append(cell.toChar());
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
