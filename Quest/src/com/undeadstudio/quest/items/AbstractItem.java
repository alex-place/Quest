package com.undeadstudio.quest.items;

public class AbstractItem {

	String name;
	String description;

	public int price;

	public enum ITEM_TYPE {
		HP_RECOVERY(), MP_RECOVERY(), DISABILITY(), CANCEL_DISABILITY(), HEADPIECE(), ARM(), ARMOR(), SHIELD(), DAMAGE(), QUEST()
	}

}
