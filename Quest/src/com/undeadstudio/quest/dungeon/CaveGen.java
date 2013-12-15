package com.undeadstudio.quest.dungeon;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.undeadstudio.quest.util.Constants;

/**
 * translated from C code found on
 * http://roguebasin.roguelikedevelopment.org/index
 * .php/Cellular_Automata_Method_for_Generating_Random_Cave-Like_Levels
 * 
 * all rights belong to original author, Jim Babcock
 */
public class CaveGen {

	public static String newline = System.getProperty("line.separator");

	private static final int TILE_FLOOR = 0;
	private static final int TILE_WALL = 1;

	public static final CaveGen instance = new CaveGen();

	private CaveGen() {
		// TODO Auto-generated constructor stub
	}

	private class GenerationParams {
		int r1_cutoff, r2_cutoff;
		int reps;
	}

	private int[][] grid, grid2;

	private int fillprob = 40;
	private int r1_cutoff = 5, r2_cutoff = 2;
	int size_y = 10, size_x = 10;

	private GenerationParams[] params_set;
	private int generations = 3;

	private Random rand = new Random();

	private int randpick() {
		if (rand.nextInt(100) < fillprob)
			return TILE_WALL;
		else
			return TILE_FLOOR;
	}

	private void initmap() {
		grid = new int[size_x][size_y];
		grid2 = new int[size_x][size_y];

		for (int x = 1; x < size_x - 1; x++)
			for (int y = 1; y < size_y - 1; y++)
				grid[x][y] = randpick();

		for (int x = 0; x < size_x; x++)
			for (int y = 0; y < size_y; y++)
				grid2[x][y] = TILE_WALL;

		for (int x = 0; x < size_x; x++)
			grid[x][0] = grid[x][size_y - 1] = TILE_WALL;
		for (int y = 0; y < size_y; y++)
			grid[0][y] = grid[size_x - 1][y] = TILE_WALL;
	}

	private void generation(GenerationParams params) {
		for (int x = 1; x < size_x - 1; x++)
			for (int y = 1; y < size_y - 1; y++) {
				int adjcount_r1 = 0, adjcount_r2 = 0;

				for (int ii = -1; ii <= 1; ii++)
					for (int jj = -1; jj <= 1; jj++) {
						if (grid[x + ii][y + jj] != TILE_FLOOR)
							adjcount_r1++;
					}
				for (int ii = x - 2; ii <= x + 2; ii++)
					for (int jj = y - 2; jj <= y + 2; jj++) {
						if (Math.abs(ii - x) == 2 && Math.abs(jj - y) == 2)
							continue;
						if (ii < 0 || jj < 0 || ii >= size_x || jj >= size_y)
							continue;
						if (grid[ii][jj] != TILE_FLOOR)
							adjcount_r2++;
					}
				if (adjcount_r1 >= params.r1_cutoff
						|| adjcount_r2 <= params.r2_cutoff)
					grid2[x][y] = TILE_WALL;
				else
					grid2[x][y] = TILE_FLOOR;
			}
		for (int x = 1; x < size_x - 1; x++)
			for (int y = 1; y < size_y - 1; y++)
				grid[x][y] = grid2[x][y];
	}

	private void printfunc() {
		System.out.println("W[0](p) = rand[0,100) < " + fillprob);

		for (int ii = 0; ii < generations; ii++) {
			System.out.print("Repeat " + params_set[ii].reps
					+ ": W'(p) = R[1](p) >= " + params_set[ii].r1_cutoff);

			if (params_set[ii].r2_cutoff >= 0)
				System.out
						.println(" || R[2](p) <= " + params_set[ii].r2_cutoff);
			else
				System.out.println();
		}
	}

	private void printmap(String filename) {
		filename = "cave";

		String fileString = "";
		int number = 0;
		FileHandle file = Gdx.files.external("Documents/My Games/Quest/levels/"
				+ filename + ".txt");

		for (int x = 0; x < size_x; x++) {
			for (int y = 0; y < size_y; y++) {
				switch (grid[x][y]) {
				case TILE_WALL:
					System.out.print(Constants.BLOCKTYPE_WALL);
					fileString += Constants.BLOCKTYPE_WALL + " ";

					break;
				case TILE_FLOOR:
					System.out.print(Constants.BLOCKTYPE_FLOOR);
					fileString += Constants.BLOCKTYPE_FLOOR + " ";

					break;
				}
			}
			System.out.println();
			fileString += newline;
		}
		file.writeString(fileString, false);

	}

	public void run(String filename) {
		params_set = new GenerationParams[generations];

		for (int ii = 0; ii < generations; ii++) {
			params_set[ii] = new GenerationParams();
			params_set[ii].r1_cutoff = r1_cutoff;
			params_set[ii].r2_cutoff = r2_cutoff;
			params_set[ii].reps = 10;
		}

		initmap();

		for (int ii = 0; ii < generations; ii++) {
			for (int jj = 0; jj < params_set[ii].reps; jj++)
				generation(params_set[ii]);
		}
		printfunc();
		printmap(filename);
	}

	public void setSize_y(int size_y) {
		this.size_y = size_y;
	}

	public void setSize(int size) {
		this.size_x = size;
		this.size_y = size;
	}

	public int getSize() {
		return size_x;
	}	

}