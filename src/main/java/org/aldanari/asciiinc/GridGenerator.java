package org.aldanari.asciiinc;

import org.aldanari.asciiinc.cells.Cell;
import org.aldanari.asciiinc.cells.MiningCell;

import java.util.Random;

public class GridGenerator {
	private final GameGrid grid;
	private final Random rand;

	public GridGenerator(GameGrid gameGrid, long seed) {
		this.grid = gameGrid;
		this.rand = new Random(seed);
	}

	public void placeMiningCell(float density, int size) {
		if (density < 0 || 1 < density) {
			throw new IllegalArgumentException("Density must be between 0 and 100");
		}
		if (size < 0 || 10 < size) {
			throw new IllegalArgumentException("Size must be between 0 and 10");
		}

		int numCluster = (int) (this.grid.getWidth() * this.grid.getHeight() * density);
		for (int i = 0; i < numCluster; i++) {
			int x = rand.nextInt(this.grid.getWidth() - 1);
			int y = rand.nextInt(this.grid.getHeight() - 1);
			this.grid.setCell(x, y, new MiningCell(x, y));
			this.fillNeighbors(x, y, rand, 0.8F);
		}

		boolean modified;
		do {
			modified = false;
			for (int x = 0; x < this.grid.getWidth(); x++) {
				for (int y = 0; y < this.grid.getHeight(); y++) {
					if (!this.grid.getCell(x, y).is(MiningCell.class)) {
						int[] north = {x - 1, y};
						int[] south = {x, y + 1};
						if (this.checkPoles(north, south)) {
							this.grid.setCell(x, y, new MiningCell(x, y));
							modified = true;
						}

						int[] east = {x + 1, y};
						int[] west = {x, y - 1};
						if (this.checkPoles(east, west)) {
							this.grid.setCell(x, y, new MiningCell(x, y));
							modified = true;
						}
					}
				}
			}
		} while (modified);
	}

	private boolean checkPoles(int[] pole1, int[] pole2) {
		if (this.grid.isInBounds(pole2[0], pole2[1]) && this.grid.isInBounds(pole1[0], pole1[1])) {
			Cell pole1Cell = this.grid.getCell(pole1[0], pole1[1]);
			Cell pole2Cell = this.grid.getCell(pole2[0], pole2[1]);
			return pole1Cell.is(MiningCell.class) && pole2Cell.is(MiningCell.class);
		}
		return false;
	}


	private void fillNeighbors(int x, int y, Random rand, float probability) {
		if (!this.grid.getCell(x, y).is(MiningCell.class)) {
			return;
		}

		int[][] neighbors = {
				{-1, -1}, {-1, 0}, {-1, 1},
				{0, -1}, {0, 1},
				{1, -1}, {1, 0}, {1, 1}
		};

		for (int[] neighbor : neighbors) {
			int newX = x + neighbor[0];
			int newY = y + neighbor[1];
			if (newX >= 0 && newX < this.grid.getWidth() && newY >= 0 && newY < this.grid.getHeight()) {
				if (rand.nextDouble(0D, 1D) < probability) {
					this.grid.setCell(newX, newY, new MiningCell(newX, newY));
					fillNeighbors(newX, newY, rand, probability / 4);
				}
			}
		}
	}
}
