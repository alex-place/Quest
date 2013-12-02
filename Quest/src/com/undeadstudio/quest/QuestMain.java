package com.undeadstudio.quest;

import com.badlogic.gdx.Game;
import com.undeadstudio.quest.game.GameScreen;

public class QuestMain extends Game {
	
	@Override
	public void create() {
		
		setScreen(new GameScreen());
	}
	
	

}