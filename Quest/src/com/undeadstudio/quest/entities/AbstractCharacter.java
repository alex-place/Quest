package com.undeadstudio.quest.entities;


public abstract class AbstractCharacter extends AbstractEntity {

	public enum DIRECTION {
		UP, DOWN, LEFT, RIGHT;
	}

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

}
