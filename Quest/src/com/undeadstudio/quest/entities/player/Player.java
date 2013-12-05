package com.undeadstudio.quest.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.undeadstudio.quest.entities.AbstractCharacter;
import com.undeadstudio.quest.entities.AbstractEntity;
import com.undeadstudio.quest.game.Assets;

public class Player extends AbstractCharacter {

	public static final String TAG = Player.class.getName();

	public final boolean collisionWithTiles = false;

	public DIRECTION direction = DIRECTION.DOWN;

	public Vector2 networkPosition = new Vector2(0, 0);

	public boolean moving = false;

	public Array<Rectangle> boundingBoxes = new Array<Rectangle>();

	public Player(Vector2 position) {
		this.position = position;
	}

	public Player(float x, float y) {
		position.x = x;
		position.y = y;

		init();

	}

	public void init() {
		dimension.set(1, 1);

		// Center image on game object
		origin.set(dimension.x / 2, dimension.y / 2);

		// Bounding box for collision detection
		bounds.set(position.x, position.y, dimension.x, dimension.y);

		updateBoundingBoxes();

	}

	public void updateBoundingBoxes() {
		boundingBoxes.clear();
		Rectangle checkBoxUp = new Rectangle(position.x, position.y + 1,
				bounds.width, bounds.height);

		Rectangle checkBoxDown = new Rectangle(position.x, position.y + -1,
				bounds.width, bounds.height);

		Rectangle checkBoxLeft = new Rectangle(position.x - 1, position.y,
				bounds.width, bounds.height);

		Rectangle checkBoxRight = new Rectangle(position.x + 1, position.y,
				bounds.width, bounds.height);

		boundingBoxes.add(checkBoxUp);
		boundingBoxes.add(checkBoxDown);
		boundingBoxes.add(checkBoxLeft);
		boundingBoxes.add(checkBoxRight);
	}

	@Override
	public void render(SpriteBatch batch) {

		Texture tex = Assets.instance.playerTexture;

		batch.draw(tex, position.x, position.y, bounds.width, bounds.height);

	}

	public void setDirection(DIRECTION direction) {
		this.direction = direction;
	}

	public void move(float x, float y, TiledMapTileLayer collisionLayer) {

		TiledMapTile tile;

		if (y == 0) {

			tile = collisionLayer.getCell(MathUtils.floor(bounds.x + x),
					MathUtils.floor(bounds.y)).getTile();

			if (!tile.getProperties().containsKey("blocked")) {

				position.x += x;
				setDirection(x > 0 ? DIRECTION.RIGHT : DIRECTION.LEFT);
			}
		}
		if (x == 0) {

			tile = collisionLayer.getCell(MathUtils.floor(bounds.x),
					MathUtils.floor(bounds.y + y)).getTile();

			if (!tile.getProperties().containsKey("blocked")) {

				position.y += y;
				setDirection(y > 0 ? DIRECTION.UP : DIRECTION.DOWN);
			}
		}

		bounds.set(position.x, position.y, dimension.x, dimension.y);
		updateBoundingBoxes();

	}

	public void move(float x, float y) {

		moving = true;

		if (y == 0 & x != 0) {
			position.x += x;
			setDirection(x > 0 ? DIRECTION.RIGHT : DIRECTION.LEFT);
		}

		if (x == 0 & y != 0) {

			position.y += y;
			setDirection(y > 0 ? DIRECTION.UP : DIRECTION.DOWN);
		}

		bounds.set(position.x, position.y, dimension.x, dimension.y);
		updateBoundingBoxes();

	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void interact(AbstractEntity entity) {
		// TODO Auto-generated method stub

	}

}
