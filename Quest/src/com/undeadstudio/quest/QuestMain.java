package com.undeadstudio.quest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.undeadstudio.quest.game.LevelScreen;
import com.undeadstudio.quest.game.ScrollTestScreen;

public class QuestMain extends Game {

	public static final String VERSION = "v0.0.1";

	public static final String NAME = "Quest " + VERSION;

	@Override
	public void create() {

		 setScreen(new LevelScreen());
//		setScreen(new ScrollTestScreen());
		Gdx.app.log("Quest", "Launching a new Quest!");
	}

}