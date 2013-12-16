package com.undeadstudio.quest.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.undeadstudio.quest.entities.AbstractEntity;
import com.undeadstudio.quest.util.Assets;

public class Chest extends Tile {

	private TextureRegion openRegion;
	private TextureRegion closedRegion;

	public boolean closed = true;

	public Chest(float x, float y) {
		this.position.x = x;
		this.position.y = y;
		init();
	}

	private void init() {
		openRegion = Assets.instance.chest.open;
		closedRegion = Assets.instance.chest.closed;
	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = closed ? closedRegion : openRegion;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x,
				origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}

	@Override
	public void interact(AbstractEntity entity) {

	}

}
