package com.undeadstudio.quest.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.undeadstudio.quest.headsupdisplay.HeadsUpDisplay;
import com.undeadstudio.quest.map.Level;
import com.undeadstudio.quest.tiles.Chest;
import com.undeadstudio.quest.util.Assets;

public class Player extends AbstractCharacter {

	private TextureRegion playerRegion;

	public Player(float x, float y) {
		this.position.x = x;
		this.position.y = y;
		init();
	}

	private void init() {
		playerRegion = Assets.instance.player.reg;
		str = 1;
		hp = 5;
	}

	@Override
	public void update(float deltaTime) {
	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = playerRegion;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x,
				origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
				reg.getRegionHeight(), false, false);
	}

	@Override
	public void interact(AbstractEntity entity) {
		if (entity instanceof Chest) {
			Chest chest = (Chest) entity;
			if (chest.closed == false) {
				Level.instance.chests.removeValue(chest, false);
			} else {
				//HeadsUpDisplay.chatMessage = "You open a chest" + "\n";
				chest.closed = false;
			}
		}

		if (entity instanceof Monster) {
			interactWithMonster((Monster) entity);
		}
	}

	private void interactWithMonster(Monster monster) {
		attack(monster);
		defend(monster);
	}

	private void attack(Monster monster) {
		monster.hp = 0;
	}

	private void defend(Monster monster) {
		hp -= (monster.str /*- vit*/);

	}
}
