package com.undeadstudio.quest.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.undeadstudio.quest.entities.AbstractEntity;

public class AbstractItem extends AbstractEntity {

	String name;
	String description;

	public int price;

	public enum ITEM_TYPE {
		HP_RECOVERY(), MP_RECOVERY(), DISABILITY(), CANCEL_DISABILITY(), HEADPIECE(), ARM(), ARMOR(), SHIELD(), DAMAGE(), QUEST()
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
