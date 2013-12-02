package com.undeadstudio.quest.entities.events;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.undeadstudio.quest.entities.AbstractCharacter.DIRECTION;
import com.undeadstudio.quest.entities.AbstractEntity;
import com.undeadstudio.quest.game.Level;

public class InteractionChecker {

	Level level;

	public InteractionChecker(Level level) {
		this.level = level;
	}

	public void checkForInteractions(AbstractEntity player, DIRECTION direction) {

		if (level.entities == null)
			return;

		Array<AbstractEntity> entities = level.entities;

		switch (direction) {

		case UP:

			Rectangle rec = player.bounds;
			rec.y += 1;

			for (AbstractEntity entity : entities) {
				if (entity != null)

					if (player.bounds.overlaps(entity.bounds)) {
						player.interact(entity);
					}

			}

			break;
		case DOWN:
			break;
		case LEFT:
			break;
		case RIGHT:
			break;
		default:
			break;
		}

	}

}
