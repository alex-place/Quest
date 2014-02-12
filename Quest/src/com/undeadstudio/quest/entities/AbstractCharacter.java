package com.undeadstudio.quest.entities;

public abstract class AbstractCharacter extends AbstractEntity {

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

	public AbstractCharacter() {

		level = 0;
		hp = 1;
		mp = 1;
		mp = 1;
		str = 1;
		vit = 1;
		dex = 1;
		agi = 1;
		magicOffense = 1;
		magicDefense = 0;

		gold = 0;
		exp = 0;

	}

}
