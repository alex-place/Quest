package com.undeadstudio.quest.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AbstractCharacter extends AbstractEntity {

	public String name;

	public int level;
	public int hp;
	public int mp;
	public int str;
	public int vit;
	public int dex;
	public int agi;
	public int magicOffense;
	public int magicDefense;

	public int gold;
	public int exp;

	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub

	}

}
