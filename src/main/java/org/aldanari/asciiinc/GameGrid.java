package org.aldanari.asciiinc;

import org.aldanari.asciiinc.cells.Cell;
import org.aldanari.asciiinc.cells.CharCell;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
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

	private void throwIfOutOfBounds(int x, int y) {
		if (!this.isInBounds(x, y)) {
			String message = String.format("Coordinate: (x=%d, y=%d) is out of bounds: (x=%d, y=%d)", x, y, this.width, this.heigth);
			throw new IndexOutOfBoundsException(message);
		}
	}

	public boolean isInBounds(int x, int y) {
		return x >= 0 && x < this.width && y >= 0 && y < this.heigth;
	}

	public Cell getCellAt(int x, int y) {
		this.throwIfOutOfBounds(x, y);
		return this.grid[y][x];
	}

	public void setCellAt(int x, int y, Cell cell) {
		this.throwIfOutOfBounds(x, y);
		this.grid[y][x] = cell;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.heigth;
	}

	public Cell[][] getGrid() {
		return this.grid;
	}

	public Cell[][] getView(int x, int y, int width, int height) {
		Cell[][] newGrid = new Cell[width][height];
		for (int i = 0; i < height; i++) {
			newGrid[i] = Arrays.copyOfRange(this.grid[y + i], x, x + width);
		}
		return newGrid;
	}

	public String getViewAsString(int x, int y, int width, int height) {
		int viewWidth = x + width;
		int viewHeight = y + height;
		this.throwIfOutOfBounds(viewWidth, viewHeight);

		StringBuilder sb = new StringBuilder();
		for (int y_ = y; y_ < viewHeight; y_++) {
			for (int x_ = x; x_ < viewWidth; x_++) {
				sb.append(this.getCellAt(x_, y_).toChar());
			}
			sb.append('\n');
		}
		// remove the last empty line
		if (!sb.isEmpty()) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	public void fillWithRandom() {
		for (int i = 0; i < this.getWidth(); i++) {
			for (int j = 0; j < this.getHeight(); j++) {
				double f = Math.random() / Math.nextDown(1f);
				char randomCode = (char) (32 * (1f - f) + 125 * f); // math magic to get random ascii char between 32 and 125
				this.setCellAt(i, j, new CharCell(randomCode));
			}
		}
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < this.getHeight(); y++) {
			for (int x = 0; x < this.getWidth(); x++) {
				sb.append(this.getCellAt(x, y).toChar());
			}
			sb.append('\n');
		}
		// Remove the last newline character
		if (!sb.isEmpty()) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	@Override
	public Iterator<Cell> iterator() {
		return new Iterator<>() {
			private int x = 0;
			private int y = 0;

			@Override
			public boolean hasNext() {
				return x <= GameGrid.this.getWidth() && y <= GameGrid.this.getHeight();
			}

			@Override
			public Cell next() throws NoSuchElementException {
				Cell cell = getCellAt(x, y);
				x++;
				if (x >= GameGrid.this.getWidth()) {
					x = 0;
					y++;
					if (y >= GameGrid.this.getHeight()) {
						throw new NoSuchElementException();
					}
				}
				return cell;
			}
		};
	}

	public Iterator<Cell> immutableIterator() {
		return Arrays.stream(this.grid).flatMap(Arrays::stream).iterator();
	}

	@Override
	public void forEach(Consumer<? super Cell> action) {
		Iterable.super.forEach(action);
	}

	@Override
	public Spliterator<Cell> spliterator() {
		return Arrays.stream(this.grid).flatMap(Arrays::stream).spliterator();
	}
}
