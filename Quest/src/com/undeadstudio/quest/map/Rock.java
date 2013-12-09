package com.undeadstudio.quest.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.undeadstudio.quest.entities.AbstractEntity;
import com.undeadstudio.quest.util.Assets;

public class Rock extends Tile {

	private TextureRegion rockRegion;

	public Rock() {
		init();
	}

	private void init() {
		rockRegion = Assets.instance.rock.reg;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = rockRegion;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x,
				origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}

	@Override
	public void interact(AbstractEntity entity) {
		// TODO Auto-generated method stub

	}

}
