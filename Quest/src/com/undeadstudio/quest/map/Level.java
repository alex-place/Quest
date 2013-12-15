package com.undeadstudio.quest.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.undeadstudio.quest.dungeon.CaveGenerator;
import com.undeadstudio.quest.dungeon.DunGen;
import com.undeadstudio.quest.entities.AbstractEntity;
import com.undeadstudio.quest.util.Constants;
import com.undeadstudio.quest.util.LevelUtil;

public class Level {

	public static final String TAG = Level.class.getName();

	public enum BLOCK_TYPE {
		EMPTY(0, 0, 0), // black
		ROCK(0, 255, 0); // green

		private int color;

		private BLOCK_TYPE(int r, int g, int b) {
			color = r << 24 | g << 16 | b << 8 | 0xff;
		}

		public boolean sameColor(int color) {
			return this.color == color;
		}

		public int getColor() {
			return color;
		}
	}

	Array<Floor> floors = new Array<Floor>();
	Array<Wall> walls = new Array<Wall>();
	Array<Door> doors = new Array<Door>();
	Array<Corridor> corridors = new Array<Corridor>();

	public Level() {
		init();
	}

	private void init() {
		
//		DunGen.instance.setChanceRoom(0);
//		DunGen.instance.generateDungeon("test", 50, 50, 500); 

		CaveGenerator.instance.run("cave");
		
		convert(LevelUtil
				.convertTextFile("cave"));

	}

	public void convert(int[][] map) {

		if (map == null) {
			Gdx.app.error(TAG, "Map conversion failed");
			return;
		}

		AbstractEntity entity = null;
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map.length; x++) {
				int type = map[x][y];

				switch (type) {
				case Constants.BLOCKTYPE_FLOOR:
					entity = new Floor(x, y);
					floors.add((Floor) entity);
					break;
				case Constants.BLOCKTYPE_WALL:
					entity = new Wall(x, y);
					walls.add((Wall) entity);
					break;
				case Constants.BLOCKTYPE_DOOR:
					entity = new Door(x, y);
					doors.add((Door) entity);
					break;
				case Constants.BLOCKTYPE_CORRIDOR:
					entity = new Corridor(x, y);
					corridors.add((Corridor) entity);
				case Constants.BLOCKTYPE_BSPNODE:
					break;
				case Constants.BLOCKTYPE_EMPTY:
					break;
				default:

					break;
				}
			}
		}

	}

	public void render(SpriteBatch batch) {
		for (Floor floor : floors) {
			floor.render(batch);
		}

		for (Wall wall : walls) {
			wall.render(batch);
		}

		for (Door door : doors) {
			door.render(batch);
		}

		for (Corridor corridor : corridors) {
			corridor.render(batch);
		}

	}

	public void update(float deltaTime) {
		for (Floor rock : floors) {
			rock.update(deltaTime);
		}

		for (Wall wall : walls) {
			wall.update(deltaTime);
		}

		for (Door door : doors) {
			door.update(deltaTime);
		}

		for (Corridor corridor : corridors) {
			corridor.update(deltaTime);
		}
	}

}