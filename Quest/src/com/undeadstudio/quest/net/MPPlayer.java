package com.undeadstudio.quest.net;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.undeadstudio.quest.entities.AbstractCharacter;
import com.undeadstudio.quest.entities.AbstractEntity;

public class MPPlayer extends AbstractCharacter {

	public float x = position.x, y = position.y;
	public int id;

	public MPPlayer() {

		bounds.set(position.x, position.y, dimension.x, dimension.y);

	}

	@Override
	public void update(float deltaTime) {
		position.x = x;
		position.y = y;
		bounds.set(position.x, position.y, dimension.x, dimension.y);
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub

	}

	@Override
	public void interact(AbstractEntity entity) {
		// TODO Auto-generated method stub

	}
}
