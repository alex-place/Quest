package com.undeadstudio.quest.interactions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.undeadstudio.quest.entities.AbstractCharacter.DIRECTION;
import com.undeadstudio.quest.entities.AbstractEntity;
import com.undeadstudio.quest.entities.player.Player;
import com.undeadstudio.quest.game.Level;
import com.undeadstudio.quest.util.Constants;

public class InteractionChecker {

	Level level;

	public static void updateBoxes(Array<Rectangle> rec, Array<Rectangle> bounds) {
		rec.removeAll(bounds, false);
		rec.addAll(bounds);

	}

	public static void updateBox(Array<Rectangle> rec, Rectangle bounds) {
		rec.removeValue(bounds, false);
		rec.addAll(bounds);

	}

	public InteractionChecker(Level level) {
		this.level = level;
	}

	public boolean checkForInteractionsWithPlayer(Player player,
			DIRECTION direction) {
		// Check for interactions with online entities

		Rectangle rectangle = player.bounds;

		switch (direction) {
		case UP:
			rectangle = player.checkBoxUp;
			break;
		case DOWN:
			rectangle = player.checkBoxDown;

			break;
		case LEFT:
			rectangle = player.checkBoxLeft;

			break;
		case RIGHT:
			rectangle = player.checkBoxRight;

			break;
		default:

			break;

		}

		// Check for other entities
		for (AbstractEntity entity : level.entities) {
			if (entity != null)

				if (rectangle.overlaps(entity.bounds)) {
					Gdx.app.log("Debug", "Overlapped" + direction);
					return true;
				}

		}
		return false;
	}

	// public boolean isPathClearForPlayer(Player player, DIRECTION direction) {
	// // Check for interactions with online entities
	//
	// Rectangle rectangle = new Rectangle(player.position.x, player.position.y,
	// player.bounds.width, player.bounds.height);
	// switch (direction) {
	// case UP:
	// rectangle = player.checkBoxUp;
	// break;
	// case DOWN:
	// rectangle = player.checkBoxDown;
	//
	// break;
	// case LEFT:
	// rectangle = player.checkBoxLeft;
	//
	// break;
	// case RIGHT:
	// rectangle = player.checkBoxRight;
	//
	// break;
	// default:
	//
	// break;
	//
	// }
	//
	// rectangle = new Rectangle(player.position.x, player.position.y,
	// player.bounds.width, player.bounds.height);
	//
	//
	// // Check for other entities
	// for (AbstractEntity entity : level.entities) {
	// if (entity != null)
	//
	// if(player.boundingBoxes.contains(entity.bounds, false))
	// return true;
	//
	// if (rectangle.overlaps(entity.bounds)) {
	// switch (direction) {
	// case UP:
	//
	// if (rectangle.y - entity.bounds.y > 0.01f)
	// return false;
	//
	// break;
	// case DOWN:
	// rectangle = player.checkBoxDown;
	//
	// if (rectangle.y - entity.bounds.y < 0.01f)
	// return false;
	//
	// break;
	// case LEFT:
	// rectangle = player.checkBoxLeft;
	//
	// System.out.println(rectangle.x - entity.bounds.x);
	// if (rectangle.x - entity.bounds.x < 0.01f)
	// return false;
	//
	// break;
	// case RIGHT:
	// rectangle = player.checkBoxRight;
	// if (rectangle.x - entity.bounds.x > 0.01f)
	// return false;
	// break;
	// default:
	//
	// break;
	//
	// }
	//
	// }
	//
	// }
	//
	// return true;
	// }

	public boolean isPathClearForPlayer(Player player, DIRECTION direction) {

		Rectangle rec = player.bounds;

		switch (direction) {
		case UP:
			for (AbstractEntity entity : level.npcEntities) {
				if (entity.bounds.y - rec.y < Constants.ENTITY_COLLISION_DISTANCE
						&& player.checkBoxUp.overlaps(entity.bounds)) {
					return false;
				}
				return true;
			}
			break;
		case DOWN:
			for (AbstractEntity entity : level.npcEntities) {
				if (entity.bounds.y - rec.y > -Constants.ENTITY_COLLISION_DISTANCE
						&& player.checkBoxDown.overlaps(entity.bounds)) {
					return false;
				}
				return true;
			}
			break;
		case LEFT:
			for (AbstractEntity entity : level.npcEntities) {
				if (entity.bounds.x - rec.x > -Constants.ENTITY_COLLISION_DISTANCE
						&& player.checkBoxLeft.overlaps(entity.bounds)) {
					return false;
				}
				return true;
			}
			break;
		case RIGHT:
			for (AbstractEntity entity : level.npcEntities) {
				if (entity.bounds.x - rec.x < Constants.ENTITY_COLLISION_DISTANCE
						&& player.checkBoxRight.overlaps(entity.bounds)) {
					return false;
				}
				return true;
			}
			break;

		}
		return true;

	}
}
