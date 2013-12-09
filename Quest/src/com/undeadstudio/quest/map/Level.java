package com.undeadstudio.quest.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.undeadstudio.quest.entities.AbstractEntity;
import com.undeadstudio.quest.entities.player.Player;

public class Level {

	public static final String TAG = Level.class.getName();

	public enum BLOCK_TYPE {
		EMPTY(0, 0, 0), // black
		ROCK(0, 255, 0), // green
		PLAYER_SPAWNPOINT(255, 255, 255); // white
		// ITEM_FEATHER(255, 0, 255), // purple
		// ITEM_GOLD_COIN(255, 255, 0); // yellow

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

	public Player player;

	Array<Rock> rocks = new Array<Rock>();

	public Level() {
		init();
	}

	private void init() {
		convertPixmap("levels/test.png");
	}

	public void convertPixmap(String filename) {
		// load image file that represents the level data
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));

		// scan pixels from top-left to bottom-right
		int lastPixel = -1;
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
				AbstractEntity obj = null;
				float offsetHeight = 0;
				// height grows from bottom to top
				float baseHeight = pixmap.getHeight() - pixelY;
				// get color of current pixel as 32-bit RGBA value
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
				// find matching color value to identify block type at (x,y)
				// point and create the corresponding game object if there is
				// a match
				if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {
					// do nothing
				}
				// rock
				else if (BLOCK_TYPE.ROCK.sameColor(currentPixel)) {
					obj = new Rock();
					float heightIncreaseFactor = 0.25f;
					offsetHeight = -2.5f;
					obj.position.set(pixelX, pixelY);
					rocks.add((Rock) obj);

				}

				// Spawn player
				else if (BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)) {
					player = new Player(new Vector2(pixelX, pixelY));
				}
			}
		}

	}

	public void render(SpriteBatch batch) {

		player.render(batch);
		for (Rock rock : rocks) {
			rock.render(batch);
		}

	}

	public void update(float deltaTime) {
		player.update(deltaTime);
		for (Rock rock : rocks) {
			rock.update(deltaTime);
		}
	}

}