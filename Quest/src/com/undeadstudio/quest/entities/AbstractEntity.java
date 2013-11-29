package com.undeadstudio.quest.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractEntity {
	
	public Vector2 position;
	public Rectangle bounds;
	public Vector2 origin;
	
	public abstract void update(float deltaTime);

	public abstract void render(SpriteBatch batch);

}
