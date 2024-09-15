package org.aldanari.asciiinc;

import org.aldanari.asciiinc.cells.Cell;
import org.aldanari.asciiinc.cells.CharCell;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class GameGrid implements Iterable<Cell> {
	private final Cell[][] grid;
	private final int heigth;
	private final int width;

	public GameGrid(int width, int height) {
		this.width = width;
		this.heigth = height;
		this.grid = new Cell[height][width];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				this.grid[y][x] = new CharCell(' ');
			}
		}
	}

	public boolean isInBounds(int x, int y) {
		return x >= 0 && x < this.width && y >= 0 && y < this.heigth;
	}

	public Cell getCell(int x, int y) {
		if (!this.isInBounds(x, y)) {
			throw new IndexOutOfBoundsException("Coordinate: (x=" + x + ", y=" + y + ") is out of bounds: (x=" + this.width + ", y=" + this.heigth + ")");
		}
		return this.grid[y][x];
	}

	public void setCell(int x, int y, Cell cell) {
		if (!this.isInBounds(x, y)) {
			throw new IndexOutOfBoundsException("Coordinate: (x=" + x + ", y=" + y + ") is out of bounds: (x=" + this.width + ", y=" + this.heigth + ")");
		}
		this.grid[y][x] = cell;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.heigth;
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

	@Override
	public Iterator<Cell> iterator() {
		return Arrays.stream(grid).flatMap(Arrays::stream).iterator();
	}

	@Override
	public void forEach(Consumer<? super Cell> action) {
		Iterable.super.forEach(action);
	}

	@Override
	public Spliterator<Cell> spliterator() {
		return Iterable.super.spliterator();
	}
}
