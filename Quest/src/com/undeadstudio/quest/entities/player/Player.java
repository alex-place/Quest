package com.undeadstudio.quest.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.undeadstudio.quest.entities.AbstractCharacter;
import com.undeadstudio.quest.game.Assets;

public class Player extends AbstractCharacter {

	public static final String TAG = Player.class.getName();

	public DIRECTION direction = DIRECTION.DOWN;

	public Vector2 networkPosition = new Vector2(0, 0);

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
		bounds.set(0, 0, dimension.x, dimension.y);

	}

	public void setDirection(DIRECTION direction) {
		this.direction = direction;
	}

	public void move(float x, float y) {
		position.y += y;
		position.x += x;

		if (y == 0)
			setDirection(x > 0 ? DIRECTION.RIGHT : DIRECTION.LEFT);
		if (x == 0)
			setDirection(y > 0 ? DIRECTION.UP : DIRECTION.DOWN);

		//Gdx.app.log(TAG, "Direction = " + direction.toString());

	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void render(SpriteBatch batch) {

		Texture tex = Assets.instance.playerTexture;
		TextureRegion reg = new TextureRegion(tex);

		batch.draw(reg.getTexture(), position.x + origin.x, position.y
				+ origin.y, origin.x, origin.y, dimension.x, dimension.y,
				scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(), false, false);

	}

}
