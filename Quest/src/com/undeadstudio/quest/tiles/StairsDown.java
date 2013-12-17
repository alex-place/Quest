package com.undeadstudio.quest.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.undeadstudio.quest.entities.AbstractEntity;
import com.undeadstudio.quest.util.Assets;

public class StairsDown extends Tile {

	private TextureRegion stairsDownRegion;

	public StairsDown(float x, float y) {
		this.position.x = x;
		this.position.y = y;
		init();
	}

	private void init() {
		stairsDownRegion = Assets.instance.stairsDown.reg;
	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = stairsDownRegion;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x,
				origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}

	@Override
	public void interact(AbstractEntity entity) {

	}

}
