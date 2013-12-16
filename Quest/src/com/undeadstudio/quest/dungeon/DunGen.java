package com.undeadstudio.quest.dungeon;

import java.util.Date; //and for getting today's date
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.undeadstudio.quest.util.Constants;

public class DunGen {
	public static String newline = System.getProperty("line.separator");

	// max size of the map
	private int ymax = 250; // 80 columns
	private int xmax = 250; // 25 rows
	private int xsize = 0;
	private int ysize = 0;

	// number of "objects" to generate on the map
	private int objects = 100;

	// define the %chance to generate either a room or a corridor on the map
	// BTW, rooms are 1st priority so actually it's enough to just define the
	// chance
	// of generating a room
	private int chanceRoom = 60;
	private int chanceCorridor = 40;

	private int corridorLength = 9;
	private int minCorridorLength = 3;
	private int corridorWidth = 9;

	// our map
	private int[] dungeon_map = {};

	// the old seed from the RNG is saved in this one
	private long oldseed = 0;

	// a list over tile types we're using
	final private int tileUnused = 0;
	final private int tileDirtWall = 1; // not in use
	final private int tileDirtFloor = 2;
	final private int tileStoneWall = 3;
	final private int tileCorridor = 4;
	final private int tileDoor = 5;
	final private int tileUpStairs = 6;
	final private int tileDownStairs = 7;
	final private int tileChest = 8;

	// misc. messages to print
	private String msgXSize = "X size of dungeon: \t";
	private String msgYSize = "Y size of dungeon: \t";
	private String msgMaxObjects = "max # of objects: \t";
	private String msgNumObjects = "# of objects made: \t";
	private String msgHelp = "";
	private String msgDetailedHelp = "";

	String[][] tiles;

	// create a new class of "dungen", so we can use all the goodies within it
	public static final DunGen instance = new DunGen();

	private DunGen() {
	}

	// setting a tile's type
	private void setCell(int x, int y, int celltype) {
		dungeon_map[x + xsize * y] = celltype;
	}

	// returns the type of a tile
	private int getCell(int x, int y) {
		return dungeon_map[x + xsize * y];
	}

	// The RNG. the seed is based on seconds from the "java epoch" ( I think..)
	// perhaps it's the same date as the unix epoch
	private int getRand(int min, int max) {
		// the seed is based on current date and the old, already used seed
		Date now = new Date();
		long seed = now.getTime() + oldseed;
		oldseed = seed;

		Random randomizer = new Random(seed);
		int n = max - min + 1;
		int i = randomizer.nextInt(n);
		if (i < 0)
			i = -i;
		// System.out.println("seed: " + seed + "\tnum:  " + (min + i));
		return min + i;
	}

	private boolean makeCorridor(int x, int y, int lenght, int direction) {
		// define the dimensions of the corridor (er.. only the width and
		// height..)
		int len = getRand(minCorridorLength, lenght);
		int floor = tileCorridor;
		int dir = 0;
		if (direction > 0 && direction < 4)
			dir = direction;

		int xtemp = 0;
		int ytemp = 0;

		int walls = 0;

		switch (dir) {
		case 0:
			// north
			if (x < 0 || x > xsize)
				return false;
			else
				xtemp = x;

			for (ytemp = y; ytemp > (y - len); ytemp--) {
				if (ytemp < 0 || ytemp > ysize)
					return false;
				if (getCell(xtemp, ytemp) == tileDirtWall) {
					walls++;
					if (walls > 1) {
						return false;
					}

				} else if (getCell(xtemp, ytemp) != tileUnused)
					return false;
			}

			for (ytemp = y; ytemp > (y - len); ytemp--) {
				setCell(xtemp, ytemp, floor);
			}

			if (getCell(xtemp, ytemp) == tileDirtWall) {
				setCell(xtemp, ytemp, tileDoor);
			}

			break;
		case 1:
			// east
			if (y < 0 || y > ysize)
				return false;
			else
				ytemp = y;
			for (xtemp = x; xtemp < (x + len); xtemp++) {
				if (xtemp < 0 || xtemp > xsize)
					return false;
				if (getCell(xtemp, ytemp) == tileDirtWall) {
					walls++;
				} else if (getCell(xtemp, ytemp) != tileUnused)
					return false;
			}

			if (walls > 1) {
				return false;
			}

			for (xtemp = x; xtemp < (x + len); xtemp++) {
				setCell(xtemp, ytemp, floor);
			}
			break;
		case 2:
			// south
			if (x < 0 || x > xsize)
				return false;
			else
				xtemp = x;

			for (ytemp = y; ytemp < (y + len); ytemp++) {
				if (ytemp < 0 || ytemp > ysize)
					return false;
				if (getCell(xtemp, ytemp) == tileDirtWall) {
					walls++;
				} else if (getCell(xtemp, ytemp) != tileUnused)
					return false;
			}

			if (walls > 1) {
				return false;
			}

			for (ytemp = y; ytemp < (y + len); ytemp++) {
				setCell(xtemp, ytemp, floor);
			}
			break;
		case 3:
			// west
			if (ytemp < 0 || ytemp > ysize)
				return false;
			else
				ytemp = y;

			for (xtemp = x; xtemp > (x - len); xtemp--) {
				if (xtemp < 0 || xtemp > xsize)
					return false;
				if (getCell(xtemp, ytemp) == tileDirtWall) {
					walls++;
				} else if (getCell(xtemp, ytemp) != tileUnused)
					return false;
			}

			if (walls > 1) {
				return false;
			}

			for (xtemp = x; xtemp > (x - len); xtemp--) {
				setCell(xtemp, ytemp, floor);
			}
			break;
		}

		return true;
	}

	private boolean makeRoom(int x, int y, int xlength, int ylength,
			int direction) {
		// define the dimensions of the room, it should be at least 4x4 tiles
		// (2x2 for walking on, the rest is walls)
		int xlen = getRand(4, xlength);
		int ylen = getRand(4, ylength);
		// the tile type it's going to be filled with
		int floor = tileDirtFloor;
		int wall = tileDirtWall;
		// choose the way it's pointing at
		int dir = 0;
		if (direction > 0 && direction < 4)
			dir = direction;

		switch (dir) {
		case 0:
			// north
			// Check if there's enough space left for it
			for (int ytemp = y; ytemp > (y - ylen); ytemp--) {
				if (ytemp < 0 || ytemp > ysize)
					return false;
				for (int xtemp = (x - xlen / 2); xtemp < (x + (xlen + 1) / 2); xtemp++) {
					if (xtemp < 0 || xtemp > xsize)
						return false;
					if (getCell(xtemp, ytemp) != tileUnused)
						return false; // no space left...
				}
			}

			// we're still here, build
			for (int ytemp = y; ytemp > (y - ylen); ytemp--) {
				for (int xtemp = (x - xlen / 2); xtemp < (x + (xlen + 1) / 2); xtemp++) {
					// start with the walls
					if (xtemp == (x - xlen / 2))
						setCell(xtemp, ytemp, wall);
					else if (xtemp == (x + (xlen - 1) / 2))
						setCell(xtemp, ytemp, wall);
					else if (ytemp == y)
						setCell(xtemp, ytemp, wall);
					else if (ytemp == (y - ylen + 1))
						setCell(xtemp, ytemp, wall);
					// and then fill with the floor
					else
						setCell(xtemp, ytemp, floor);
				}
			}
			break;
		case 1:
			// east
			for (int ytemp = (y - ylen / 2); ytemp < (y + (ylen + 1) / 2); ytemp++) {
				if (ytemp < 0 || ytemp > ysize)
					return false;
				for (int xtemp = x; xtemp < (x + xlen); xtemp++) {
					if (xtemp < 0 || xtemp > xsize)
						return false;
					if (getCell(xtemp, ytemp) != tileUnused)
						return false;
				}
			}

			for (int ytemp = (y - ylen / 2); ytemp < (y + (ylen + 1) / 2); ytemp++) {
				for (int xtemp = x; xtemp < (x + xlen); xtemp++) {

					if (xtemp == x)
						setCell(xtemp, ytemp, wall);
					else if (xtemp == (x + xlen - 1))
						setCell(xtemp, ytemp, wall);
					else if (ytemp == (y - ylen / 2))
						setCell(xtemp, ytemp, wall);
					else if (ytemp == (y + (ylen - 1) / 2))
						setCell(xtemp, ytemp, wall);

					else
						setCell(xtemp, ytemp, floor);
				}
			}
			break;
		case 2:
			// south
			for (int ytemp = y; ytemp < (y + ylen); ytemp++) {
				if (ytemp < 0 || ytemp > ysize)
					return false;
				for (int xtemp = (x - xlen / 2); xtemp < (x + (xlen + 1) / 2); xtemp++) {
					if (xtemp < 0 || xtemp > xsize)
						return false;
					if (getCell(xtemp, ytemp) != tileUnused)
						return false;
				}
			}

			for (int ytemp = y; ytemp < (y + ylen); ytemp++) {
				for (int xtemp = (x - xlen / 2); xtemp < (x + (xlen + 1) / 2); xtemp++) {

					if (xtemp == (x - xlen / 2))
						setCell(xtemp, ytemp, wall);
					else if (xtemp == (x + (xlen - 1) / 2))
						setCell(xtemp, ytemp, wall);
					else if (ytemp == y)
						setCell(xtemp, ytemp, wall);
					else if (ytemp == (y + ylen - 1))
						setCell(xtemp, ytemp, wall);

					else
						setCell(xtemp, ytemp, floor);
				}
			}
			break;
		case 3:
			// west
			for (int ytemp = (y - ylen / 2); ytemp < (y + (ylen + 1) / 2); ytemp++) {
				if (ytemp < 0 || ytemp > ysize)
					return false;
				for (int xtemp = x; xtemp > (x - xlen); xtemp--) {
					if (xtemp < 0 || xtemp > xsize)
						return false;
					if (getCell(xtemp, ytemp) != tileUnused)
						return false;
				}
			}

			for (int ytemp = (y - ylen / 2); ytemp < (y + (ylen + 1) / 2); ytemp++) {
				for (int xtemp = x; xtemp > (x - xlen); xtemp--) {

					if (xtemp == x)
						setCell(xtemp, ytemp, wall);
					else if (xtemp == (x - xlen + 1))
						setCell(xtemp, ytemp, wall);
					else if (ytemp == (y - ylen / 2))
						setCell(xtemp, ytemp, wall);
					else if (ytemp == (y + (ylen - 1) / 2))
						setCell(xtemp, ytemp, wall);

					else
						setCell(xtemp, ytemp, floor);
				}
			}
			break;
		}

		// yay, all done
		return true;
	}

	// used to print the map on the screen
	public void showDungeon(String filename) {

		String fileString = "";
		int number = 0;
		FileHandle file = Gdx.files.external("Documents/My Games/Quest/levels/"
				+ filename + ".txt");

		tiles = new String[ysize][xsize];
		for (int y = 0; y < ysize; y++) {
			for (int x = 0; x < xsize; x++) {
				switch (getCell(x, y)) {
				case tileUnused:
					// System.out.print(" ");
					fileString += Constants.BLOCKTYPE_EMPTY + " ";
					break;
				case tileDirtWall:
					// System.out.print("+");
					fileString += Constants.BLOCKTYPE_WALL + " ";
					break;
				case tileDirtFloor:
					// System.out.print(".");
					fileString += Constants.BLOCKTYPE_FLOOR + " ";
					break;
				case tileStoneWall:
					// System.out.print("O");
					fileString += Constants.BLOCKTYPE_EMPTY + " ";
					break;
				case tileCorridor:
					// System.out.print("#");
					fileString += Constants.BLOCKTYPE_CORRIDOR + " ";
					break;
				case tileDoor:
					// System.out.print("D");
					fileString += Constants.BLOCKTYPE_DOOR + " ";
					break;
				case tileUpStairs:
					// System.out.print("<");
					fileString += Constants.BLOCKTYPE_FLOOR + " ";
					break;
				case tileDownStairs:
					// System.out.print(">");
					fileString += Constants.BLOCKTYPE_FLOOR + " ";
					break;
				case tileChest:
					// System.out.print("*");
					fileString += Constants.BLOCKTYPE_FLOOR + " ";
					break;
				}
			}
			if (xsize <= xmax)
				// System.out.println();
				fileString += newline;
		}

		// System.out.println(fileString);

		file.writeString(fileString, false);

	}

	public boolean createDungeon(int inx, int iny, int inobj) {
		if (inobj < 1)
			objects = 100;
		else
			objects = inobj;

		// adjust the size of the map, if it's smaller or bigger than the limits
		if (inx < 3)
			xsize = 3;
		else if (inx > xmax)
			xsize = xmax;
		else
			xsize = inx;

		if (iny < 3)
			ysize = 3;
		else if (iny > ymax)
			ysize = ymax;
		else
			ysize = iny;

		dungeon_map = new int[xsize * ysize];

		for (int y = 0; y < ysize; y++) {
			for (int x = 0; x < xsize; x++) {
				if (y == 0)
					setCell(x, y, tileStoneWall);
				else if (y == ysize - 1)
					setCell(x, y, tileStoneWall);
				else if (x == 0)
					setCell(x, y, tileStoneWall);
				else if (x == xsize - 1)
					setCell(x, y, tileStoneWall);
				else
					setCell(x, y, tileUnused);
			}
		}

		makeRoom(xsize / 2, ysize / 2, corridorWidth, corridorLength,
				getRand(0, 3));

		int currentFeatures = 1;

		for (int countingTries = 0; countingTries < 500; countingTries++) {
			if (currentFeatures == objects) {
				break;
			}

			int newx = 0;
			int xmod = 0;
			int newy = 0;
			int ymod = 0;
			int validTile = -1;
			for (int testing = 0; testing < 500; testing++) {
				newx = getRand(1, xsize - 1);
				newy = getRand(1, ysize - 1);
				validTile = -1;
				if (getCell(newx, newy) == tileDirtWall
						|| getCell(newx, newy) == tileCorridor) {
					if (getCell(newx, newy + 1) == tileDirtFloor
							|| getCell(newx, newy + 1) == tileCorridor) {
						validTile = 0; //
						xmod = 0;
						ymod = -1;
					} else if (getCell(newx - 1, newy) == tileDirtFloor
							|| getCell(newx - 1, newy) == tileCorridor) {
						validTile = 1; //
						xmod = +1;
						ymod = 0;
					} else if (getCell(newx, newy - 1) == tileDirtFloor
							|| getCell(newx, newy - 1) == tileCorridor) {
						validTile = 2; //
						xmod = 0;
						ymod = +1;
					} else if (getCell(newx + 1, newy) == tileDirtFloor
							|| getCell(newx + 1, newy) == tileCorridor) {
						validTile = 3; //
						xmod = -1;
						ymod = 0;
					}

					if (validTile > -1) {
						if (getCell(newx, newy + 1) == tileDoor) // north
							validTile = -1;
						else if (getCell(newx - 1, newy) == tileDoor)// east
							validTile = -1;
						else if (getCell(newx, newy - 1) == tileDoor)// south
							validTile = -1;
						else if (getCell(newx + 1, newy) == tileDoor)// west
							validTile = -1;
					}

					if (validTile > -1)
						break;
				}
			}
			if (validTile > -1) {
				int feature = getRand(0, 100);
				if (feature <= chanceRoom) { // a new room
					if (makeRoom((newx + xmod), (newy + ymod), corridorWidth,
							corridorLength, validTile)) {
						currentFeatures++; // add to our quota

						setCell(newx, newy, tileDoor);

						setCell((newx + xmod), (newy + ymod), tileDirtFloor);
					}
				} else if (feature >= chanceRoom) { // new corridor
					if ((getCell(newx, newy) == tileCorridor)) {
						validTile = getRand(0, 4);
					}
					if (makeCorridor((newx + xmod), (newy + ymod),
							corridorLength, validTile)) {
						currentFeatures++;

						if (!(getCell(newx, newy) == tileCorridor)) {
							setCell(newx, newy, tileDoor);
						}
					} else {
						validTile = getRand(0, 4);

						if (makeCorridor((newx + xmod), (newy + ymod),
								corridorLength, validTile)) {
							currentFeatures++;

							if (!(getCell(newx, newy) == tileCorridor)) {
								setCell(newx, newy, tileDoor);
							}
						}
					}
				}
			}
		}

		return true;
	}

	public void generateDungeon(String filename, int x, int y, int objects) {
		if (instance.createDungeon(x, y, objects)) {
			instance.showDungeon(filename);
		}
	}

	public int getMinCorridorLength() {
		return minCorridorLength;
	}

	public void setMinCorridorLength(int minCorridorLength) {
		this.minCorridorLength = minCorridorLength;
	}

	public int getYmax() {
		return ymax;
	}

	public void setYmax(int ymax) {
		this.ymax = ymax;
	}

	public int getXmax() {
		return xmax;
	}

	public void setXmax(int xmax) {
		this.xmax = xmax;
	}

	public int getXsize() {
		return xsize;
	}

	public void setXsize(int xsize) {
		this.xsize = xsize;
	}

	public int getYsize() {
		return ysize;
	}

	public void setYsize(int ysize) {
		this.ysize = ysize;
	}

	public int getChanceRoom() {
		return chanceRoom;
	}

	public void setChanceRoom(int chanceRoom) {
		this.chanceRoom = chanceRoom;
	}

	public int getCorridorLength() {
		return corridorLength;
	}

	public void setMaxCorridorLength(int corridorLength) {
		this.corridorLength = corridorLength;
	}

	public int getCorridorWidth() {
		return corridorWidth;
	}

	public void setCorridorWidth(int corridorWidth) {
		this.corridorWidth = corridorWidth;
	}

}