package com.undeadstudio.quest.entities.events;

import com.undeadstudio.quest.entities.AbstractEntity;

public interface Interaction {

	public void interact(AbstractEntity sender, AbstractEntity reciever);

}
