package com.undeadstudio.quest.entities.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.undeadstudio.quest.entities.AbstractCharacter;
import com.undeadstudio.quest.entities.AbstractEntity;
import com.undeadstudio.quest.game.Level;
import com.undeadstudio.quest.interactions.InteractionChecker;
import com.undeadstudio.quest.util.Assets;

public class Player extends AbstractCharacter {
	Level level;

	public static final String TAG = Player.class.getName();

	public final boolean collisionWithTiles = false;

	public DIRECTION direction = DIRECTION.DOWN;

	public Vector2 networkPosition = new Vector2(0, 0);

	public Array<Rectangle> boundingBoxes = new Array<Rectangle>();
	public Rectangle checkBoxUp;
	public Rectangle checkBoxDown;
	public Rectangle checkBoxLeft;
	public Rectangle checkBoxRight;

	private InteractionChecker interactionChecker;

	public Player(Vector2 position) {
		this.position = position;
	}

	public Player(float x, float y, Level level) {
		this.level = level;
		interactionChecker = new InteractionChecker(level);

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
		checkBoxUp = new Rectangle(position.x, position.y + 1, bounds.width,
				bounds.height);

		checkBoxDown = new Rectangle(position.x, position.y + -1, bounds.width,
				bounds.height);

		checkBoxLeft = new Rectangle(position.x - 1, position.y, bounds.width,
				bounds.height);

		checkBoxRight = new Rectangle(position.x + 1, position.y, bounds.width,
				bounds.height);

		boundingBoxes.add(checkBoxUp);
		boundingBoxes.add(checkBoxDown);
		boundingBoxes.add(checkBoxLeft);
		boundingBoxes.add(checkBoxRight);
		boundingBoxes.add(bounds);

	}

	@Override
	public void render(SpriteBatch batch) {

		Texture tex = Assets.instance.playerTexture;

		batch.draw(tex, position.x, position.y, bounds.width, bounds.height);

	}

	public void setDirection(DIRECTION direction) {
		this.direction = direction;
	}

	public void move(float x, float y) {

		if (y == 0 & x != 0) {
			if (interactionChecker.isPathClearForPlayer(this, direction))
				position.x += x;
		}

		if (x == 0 & y != 0) {
			if (interactionChecker.isPathClearForPlayer(this, direction))
				position.y += y;
		}

		bounds.set(position.x, position.y, dimension.x, dimension.y);
		updateBoundingBoxes();
		System.out.println(direction);
	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void interact(AbstractEntity entity) {
	}

}
