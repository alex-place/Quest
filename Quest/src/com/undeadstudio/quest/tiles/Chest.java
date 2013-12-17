package com.undeadstudio.quest.tiles;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.undeadstudio.quest.entities.AbstractEntity;
import com.undeadstudio.quest.util.Assets;

public class Chest extends Tile {

	private TextureRegion closedRegion;
	private TextureRegion openRegion;
	public boolean closed = true;

	public Chest(float x, float y) {
		this.position.x = x;
		this.position.y = y;
		init();
	}

	private void init() {
		closedRegion = Assets.instance.chest.reg;
		openRegion = Assets.instance.chest.open;

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
