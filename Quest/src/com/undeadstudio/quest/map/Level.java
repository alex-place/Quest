package com.undeadstudio.quest.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.undeadstudio.quest.controller.PlayerController;
import com.undeadstudio.quest.dungeon.CaveGen;
import com.undeadstudio.quest.entities.AbstractEntity;
import com.undeadstudio.quest.entities.Monster;
import com.undeadstudio.quest.entities.Player;
import com.undeadstudio.quest.headsupdisplay.HeadsUpDisplay;
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

	public static Level instance = new Level();

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

	public Array<Floor> floors = new Array<Floor>(Floor.class);
	public Array<Wall> walls = new Array<Wall>(Wall.class);
	public Array<Door> doors = new Array<Door>(Door.class);
	public Array<Corridor> corridors = new Array<Corridor>(Corridor.class);
	public Array<Chest> chests = new Array<Chest>(Chest.class);
	public Array<Tile> stairs = new Array<Tile>(Tile.class);
	public Array<Monster> monsters = new Array<Monster>(Monster.class);
	public Array<Player> players = new Array<Player>(Player.class);

	public boolean[][] walkable;

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

		shrinkArrays();

	}

	private void sprinkleGoodies() {
		sprinkleStairs();

		sprinkleChests(10);

		sprinkleMonsters(10);

		sprinklePlayer();

	}

	private void shrinkArrays() {
		chests.shrink();
		corridors.shrink();
		doors.shrink();
		floors.shrink();
		monsters.shrink();
		players.shrink();
		stairs.shrink();
	}

	private void sprinklePlayer() {
		AbstractEntity entity = null;
		Vector2 freePlace = findFreePlace(Constants.BLOCKTYPE_FLOOR);
		entity = new Player(freePlace.x, freePlace.y);
		players.add((Player) entity);
		conPlayer = new PlayerController((Player) entity);
		input.addProcessor(conPlayer);
	}

	private void sprinkleStairs() {
		AbstractEntity entity = null;
		Vector2 freePlace = findFreePlace(Constants.BLOCKTYPE_FLOOR);
		entity = new StairsUp(freePlace.x, freePlace.y);
		stairs.add((StairsUp) entity);

		freePlace = findFreePlace(Constants.BLOCKTYPE_FLOOR);
		entity = new StairsDown(freePlace.x, freePlace.y);
		stairs.add((StairsDown) entity);
	}

	private void sprinkleChests(int number) {

		for (int x = 0; x < number; x++) {
			AbstractEntity entity = null;
			Vector2 freePlace = findFreePlace(Constants.BLOCKTYPE_FLOOR);
			entity = new Chest(freePlace.x, freePlace.y);
			chests.add((Chest) entity);
		}

	}

	private void sprinkleMonsters(int number) {
		for (int x = 0; x < number; x++) {
			AbstractEntity entity = null;
			Vector2 freePlace = findFreePlace(Constants.BLOCKTYPE_FLOOR);
			entity = new Monster(freePlace.x, freePlace.y);
			monsters.add((Monster) entity);
		}
	}

	private void convert(int[][] map) {

		if (map == null) {
			Gdx.app.error(TAG, "Map conversion failed");
			return;
		}

		walkable = new boolean[map.length][map.length];

		AbstractEntity entity = null;
		// We are assuming that the map read in is a perfect square
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map.length; x++) {
				int type = map[x][y];
				walkable[x][y] = false;
				switch (type) {
				case Constants.BLOCKTYPE_FLOOR:
					entity = new Floor(x, y);
					floors.add((Floor) entity);
					walkable[x][y] = false;
					break;
				case Constants.BLOCKTYPE_WALL:
					entity = new Wall(x, y);
					walls.add((Wall) entity);
					break;
				case Constants.BLOCKTYPE_DOOR:
					entity = new Door(x, y);
					doors.add((Door) entity);
					walkable[x][y] = false;
					break;
				case Constants.BLOCKTYPE_CORRIDOR:
					entity = new Corridor(x, y);
					corridors.add((Corridor) entity);
					walkable[x][y] = false;
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

		for (Monster entity : monsters) {
			if (entity.hp <= 0) {
				monsters.removeValue(entity, false);
			}
			entity.update(deltaTime);

		}

		for (Player player : players) {
			player.update(deltaTime);
		}

	}

	private Vector2 findFreePlace(int blocktype) {
		switch (blocktype) {
		case Constants.BLOCKTYPE_FLOOR:
			return floors.random().position;
		case Constants.BLOCKTYPE_WALL:
			return walls.random().position;
		default:
			System.out
					.println("Level.findFreePlace() recieved unhandled blocktype: "
							+ blocktype);
			return null;
		}
	}

	public AbstractEntity getCell(int blocktype, float x, float y) {
		switch (blocktype) {

		case Constants.BLOCKTYPE_FLOOR:
			for (Floor floor : floors) {
				if (floor.position.x == x && floor.position.y == y) {
					System.out.println("x: " + floor.position.x + " y: "
							+ floor.position.y);
					return floor;
				}
			}
			break;
		case Constants.BLOCKTYPE_WALL:
			for (Wall wall : walls) {
				if (wall.position.x == x && wall.position.y == y) {
					System.out.println("x: " + wall.position.x + " y: "
							+ wall.position.y);
					return wall;
				}
			}

			break;
		case Constants.BLOCKTYPE_CHEST:
			for (Chest chest : chests) {
				if (chest.position.x == x && chest.position.y == y) {
					System.out.println("x: " + chest.position.x + " y: "
							+ chest.position.y);
					return chest;
				}
			}
			break;

		default:
			System.out
					.println("Level.getCell() recieved unhandled blocktype : "
							+ blocktype);
			break;
		}

		return null;
	}

	public void move(AbstractEntity entity, float x, float y) {

		AbstractEntity tile = null;

		tile = getCell(Constants.BLOCKTYPE_CHEST, entity.position.x + x,
				entity.position.y + y);
		if (tile instanceof Chest && entity instanceof Player) {
			entity.interact(tile);

			return;
		}

		if (collideMonsters(getPlayer(), x, y) == true) {
			return;
		}

		tile = getCell(Constants.BLOCKTYPE_FLOOR, entity.position.x + x,
				entity.position.y + y);

		if (tile instanceof Floor) {

			if (walkable[(int) (entity.position.x += x)][(int) (entity.position.y += y)]) {
				entity.position.x += x;
				entity.position.y += y;
			}

		}

	}

	public boolean collideMonsters(AbstractEntity entity, float x, float y) {

		for (Monster monster : monsters.toArray()) {
			if (monster.position.x == entity.position.x
					&& monster.position.y == entity.position.y) {
				monsters.removeValue(monster, true);

				HeadsUpDisplay.chatColor = Color.RED;
				HeadsUpDisplay.chatMessage = "You have slain a "
						+ monster.getName();
				return true;
			}
		}

		return false;
	}

	public void moveMonsters() {
		for (Monster entity : monsters) {
			moveRandom(entity, 1);

		}
	}

	public void moveRandom(AbstractEntity entity, int speed) {
		if (MathUtils.randomBoolean(0.9f)) {
			if (MathUtils.randomBoolean()) {
				move(entity, MathUtils.random(-speed, speed), 0);
			} else {
				move(entity, 0, MathUtils.random(-speed, speed));

			}
		}
	}

	public Player getPlayer() {
		return players.first();
	}

}