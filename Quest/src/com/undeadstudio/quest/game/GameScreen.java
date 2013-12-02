package com.undeadstudio.quest.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {

	Level level;

	SpriteBatch batch;


	public GameScreen() {
		init();
	}

	public void init() {
		Assets.instance.init(new AssetManager());
		level = new Level("test.tmx");

	}

	public void update(float deltaTime) {
			level.update(deltaTime);

	}

	@Override
	public void render(float deltaTime) {
		update(deltaTime);

		level.render(deltaTime);
	}

	@Override
	public void resize(int width, int height) {
		level.resize(width, height);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		level.dispose();
	}

}
