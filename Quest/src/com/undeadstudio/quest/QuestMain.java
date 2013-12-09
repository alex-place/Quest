package com.undeadstudio.quest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.undeadstudio.quest.game.LevelScreen;

public class QuestMain extends Game {

	@Override
	public void create() {

		setScreen(new LevelScreen());
		Gdx.app.log("Quest", "Launching a new Quest!");
	}

}