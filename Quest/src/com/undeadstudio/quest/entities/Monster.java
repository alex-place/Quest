package com.undeadstudio.quest.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.undeadstudio.quest.tiles.Tile;
import com.undeadstudio.quest.util.Assets;

public class Monster extends Tile {

	private TextureRegion monsterRegion;

	public int hp = 1;
	public int str = 1;

	private String name = "Bee";

	public Monster(float x, float y) {
		this.position.x = x;
		this.position.y = y;
		init();
	}

	public String getName() {
		return name;

	}

	private void init() {
		monsterRegion = Assets.instance.monster.reg;
		hp = 1;
		str = 1;
	}

	@Override
	public void update(float deltaTime) {

	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = monsterRegion;
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
