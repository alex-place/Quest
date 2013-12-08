package com.undeadstudio.quest.net;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.undeadstudio.quest.entities.AbstractCharacter;
import com.undeadstudio.quest.entities.AbstractEntity;
import com.undeadstudio.quest.interactions.InteractionChecker;

public class MPPlayer extends AbstractCharacter {

	public float x = position.x, y = position.y;
	public int id;

	public MPPlayer(float x, float y) {
		this.x = x;
		this.y = y;
		bounds.set(position.x, position.y, dimension.x, dimension.y);
	}

	@Override
	public void update(float deltaTime) {
		position.x = x;
		position.y = y;
		bounds.set(position.x, position.y, dimension.x, dimension.y);
		InteractionChecker.updateBox(NetClient.boundingBoxes, bounds);
	}

	@Override
	public void render(SpriteBatch batch) {

	}

	@Override
	public void interact(AbstractEntity entity) {

	}
}
