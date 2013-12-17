package com.undeadstudio.quest.tiles;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.undeadstudio.quest.entities.AbstractEntity;
import com.undeadstudio.quest.util.Assets;

public class Wall extends Tile {

	private TextureRegion wallRegion;

	public Wall(float x, float y) {
		this.position.x = x;
		this.position.y = y;
		init();
	}

	public void init() {
		wallRegion = Assets.instance.wall.reg;
	}

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = wallRegion;
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
