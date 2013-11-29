package com.undeadstudio.quest.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Array;
import com.undeadstudio.quest.entities.AbstractEntity;

public class Level {

	Array<AbstractEntity> entities;
	TiledMap map;

	public Level() {
		init();
	}

	public void init() {
		entities = new Array<AbstractEntity>();
	}

	public void render(SpriteBatch batch) {
		for (AbstractEntity entity : entities) {
			if (entity != null)
				entity.render(batch);
		}
	}

	public void update(float deltaTime) {
		for (AbstractEntity entity : entities) {
			if (entity != null)
				entity.update(deltaTime);
		}
	}

}
