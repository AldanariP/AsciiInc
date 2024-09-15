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

	public void placeMiningCell(float density, int size, float entropy) {
		if (density < 0 || 1 < density) {
			throw new IllegalArgumentException("Density must be between 0 and 1");
		}
		if (size < 0 || 10 < size) {
			throw new IllegalArgumentException("Size must be between 0 and 10");
		}

		int numCluster = (int) (this.grid.getWidth() * this.grid.getHeight() * density);
		for (int i = 0; i < numCluster; i++) {
			int x = this.rand.nextInt(this.grid.getWidth() - 1);
			int y = this.rand.nextInt(this.grid.getHeight() - 1);
			this.grid.setCellAt(x, y, new MiningCell(x, y));
			this.fillNeighbors(x, y, size, 0, entropy);
		}

		this.fillGaps();
	}


	private void fillNeighbors(int x, int y, int size, int depth, float entropy) {
		if (!this.grid.getCellAt(x, y).is(MiningCell.class)) {
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
			if (this.grid.isInBounds(newX, newY)) {
				double k = entropy / size; // stepness factor, <10 seems to give wanted results
				double newProbability = 1.0 / (1.0 + Math.exp(k * (depth - (double) size / 2))); // expodential decay
				if (this.rand.nextDouble() < newProbability) {
					this.grid.setCellAt(newX, newY, new MiningCell(newX, newY));
					this.fillNeighbors(newX, newY, size, depth + 1, entropy);
				}
			}
		}
	}

	private void fillGaps() {
		boolean modified;
		do {
			modified = false;
			for (int x = 0; x < this.grid.getWidth(); x++) {
				for (int y = 0; y < this.grid.getHeight(); y++) {
					if (!this.grid.getCellAt(x, y).is(MiningCell.class)) {
						int[] west = {x - 1, y};
						int[] east = {x + 1, y};
//						To debate
//						int[] north = {x, y + 1};
//						int[] south = {x, y - 1};
						if (this.checkPoles(east, west)) {
							this.grid.setCellAt(x, y, new MiningCell(x, y));
							modified = true;
							break;
						}
					}
				}
			}
		} while (modified);
	}

	private boolean checkPoles(int[] pole1, int[] pole2) {
		if (this.grid.isInBounds(pole2[0], pole2[1]) && this.grid.isInBounds(pole1[0], pole1[1])) {
			Cell pole1Cell = this.grid.getCellAt(pole1[0], pole1[1]);
			Cell pole2Cell = this.grid.getCellAt(pole2[0], pole2[1]);
			return pole1Cell.is(MiningCell.class) && pole2Cell.is(MiningCell.class);
		}
		return false;
	}
}
