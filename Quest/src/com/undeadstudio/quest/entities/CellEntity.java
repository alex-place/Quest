package com.undeadstudio.quest.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;

public class CellEntity extends AbstractEntity {
	Cell cell;

	public CellEntity(float x, float y, Cell cell) {
		position.x = x;
		position.y = y;
		this.cell = cell;
		bounds = new Rectangle(x, y, dimension.x, dimension.y);
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

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
