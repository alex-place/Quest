package com.undeadstudio.quest.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.undeadstudio.quest.controller.PlayerController;
import com.undeadstudio.quest.dungeon.CaveGen;
import com.undeadstudio.quest.entities.AbstractEntity;
import com.undeadstudio.quest.entities.Monster;
import com.undeadstudio.quest.entities.Player;
import com.undeadstudio.quest.tiles.Chest;
import com.undeadstudio.quest.tiles.Corridor;
import com.undeadstudio.quest.tiles.Door;
import com.undeadstudio.quest.tiles.Floor;
import com.undeadstudio.quest.tiles.StairsDown;
import com.undeadstudio.quest.tiles.StairsUp;
import com.undeadstudio.quest.tiles.Tile;
import com.undeadstudio.quest.tiles.Wall;
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

	InputMultiplexer input = new InputMultiplexer();
	PlayerController conPlayer;

	Array<Floor> floors = new Array<Floor>();
	Array<Wall> walls = new Array<Wall>();
	Array<Door> doors = new Array<Door>();
	Array<Corridor> corridors = new Array<Corridor>();
	Array<Chest> chests = new Array<Chest>();
	Array<Tile> stairs = new Array<Tile>();
	Array<AbstractEntity> monsters = new Array<AbstractEntity>();
	Array<Player> players = new Array<Player>();

	public Level() {
		init();
	}

	private void init() {
		Gdx.input.setInputProcessor(input);
		String filename = "test";

		// DunGen.instance.setChanceRoom(75);
		// DunGen.instance.setMinCorridorLength(2);
		// DunGen.instance.setMaxCorridorLength(16);
		// DunGen.instance.generateDungeon(filename, 50, 50, 500);

		CaveGen.instance.setSize(40);
		CaveGen.instance.generateCave(filename);

		convert(LevelUtil.convertTextFile(filename));

		sprinkleGoodies();
	}

	public void sprinkleGoodies() {
		sprinkleStairs();

		sprinkleChests(10);

		sprinkleMonsters(10);

		sprinklePlayer();

	}

	public void sprinklePlayer() {
		AbstractEntity entity = null;
		Vector2 freePlace = findFreePlace(Constants.BLOCKTYPE_FLOOR);
		entity = new Player(freePlace.x, freePlace.y);
		players.add((Player) entity);
		conPlayer = new PlayerController((Player) entity);
		input.addProcessor(conPlayer);
	}

	public void sprinkleStairs() {
		AbstractEntity entity = null;
		Vector2 freePlace = findFreePlace(Constants.BLOCKTYPE_FLOOR);
		entity = new StairsUp(freePlace.x, freePlace.y);
		stairs.add((StairsUp) entity);

		freePlace = findFreePlace(Constants.BLOCKTYPE_FLOOR);
		entity = new StairsDown(freePlace.x, freePlace.y);
		stairs.add((StairsDown) entity);
	}

	public void sprinkleChests(int number) {

		for (int x = 0; x < number; x++) {
			AbstractEntity entity = null;
			Vector2 freePlace = findFreePlace(Constants.BLOCKTYPE_FLOOR);
			entity = new Chest(freePlace.x, freePlace.y);
			chests.add((Chest) entity);
		}

	}

	public void sprinkleMonsters(int number) {
		for (int x = 0; x < number; x++) {
			AbstractEntity entity = null;
			Vector2 freePlace = findFreePlace(Constants.BLOCKTYPE_FLOOR);
			entity = new Monster(freePlace.x, freePlace.y);
			monsters.add((Monster) entity);
		}
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

		/*
		 * Draw the bottom tiles first.
		 */
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

		/*
		 * Then draw the static entities.
		 */

		batch.setColor(Color.toFloatBits(255, 200, 100, 255));
		for (Chest chest : chests) {
			chest.render(batch);
		}

		for (Tile tile : stairs)
			tile.render(batch);

		batch.setColor(Color.WHITE);

		/*
		 * Now draw the dynamic entities.
		 */

		for (AbstractEntity entity : monsters) {
			entity.render(batch);
		}

		for (Player player : players) {
			player.render(batch);
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

		for (Chest chest : chests) {
			chest.update(deltaTime);
		}

		for (Tile tile : stairs) {
			tile.update(deltaTime);
		}

		for (AbstractEntity entity : monsters) {
			entity.update(deltaTime);
		}

		for (Player player : players) {
			player.update(deltaTime);
		}
	}

	public Vector2 findFreePlace(int blockType) {
		switch (blockType) {
		case Constants.BLOCKTYPE_FLOOR:
			// return floors.get(MathUtils.random(0, floors.size)).position;
			return floors.random().position;

		default:
			return null;
		}
	}

	public static void move(AbstractEntity entity, float x, float y) {
		entity.position.x += x;
		entity.position.y += y;
	}

}