package com.undeadstudio.quest.interactions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.undeadstudio.quest.entities.AbstractCharacter.DIRECTION;
import com.undeadstudio.quest.entities.AbstractEntity;
import com.undeadstudio.quest.entities.player.Player;
import com.undeadstudio.quest.game.Level;

public class InteractionChecker {

	Level level;
	public Array<Rectangle> boundingBoxes = new Array<Rectangle>();

	public InteractionChecker(Level level) {
		this.level = level;
	}

	public void checkForInteractionsWithPlayer(Player player,
			DIRECTION direction) {

		for (Rectangle rec : player.boundingBoxes) {
			for (Rectangle rec2 : boundingBoxes) {
				if (rec.overlaps(rec2)) {
					Gdx.app.log("Debug", "overlaps");
				}
			}
		}

	}

}
