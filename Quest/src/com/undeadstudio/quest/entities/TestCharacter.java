package com.undeadstudio.quest.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.undeadstudio.quest.util.Assets;

public class TestCharacter extends AbstractCharacter {

	public TestCharacter(float x, float y) {
		position.x = x;
		position.y = y;

		init();
	}

	private void init() {
		dimension.set(1, 1);

		// Center image on game object
		origin.set(dimension.x / 2, dimension.y / 2);

		// Bounding box for collision detection
		bounds.set(position.x, position.y, dimension.x, dimension.y);
	}

	@Override
	public void update(float deltaTime) {
		bounds.x = position.x;
		bounds.y = position.y;

	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(Assets.instance.playerTexture, position.x, position.y,
				bounds.width, bounds.height);
	}

	@Override
	public void interact(AbstractEntity entity) {

	}

}
