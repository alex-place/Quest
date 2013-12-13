package com.undeadstudio.quest.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.undeadstudio.quest.map.Level;
import com.undeadstudio.quest.util.Assets;
import com.undeadstudio.quest.util.CameraHelper;
import com.undeadstudio.quest.util.Constants;

public class LevelScreen implements Screen {

	SpriteBatch batch;
	OrthographicCamera camera;
	CameraHelper helper;
	AssetManager assetManager;
	Level level;
	boolean paused = false;

	public LevelScreen() {
		init();
	}

	private void init() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,
				Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		helper = new CameraHelper();
		helper.applyTo(camera);

		assetManager = new AssetManager();
		Assets.instance.init(assetManager);
		level = new Level();

		// helper.setTarget(level.player);
	}

	public void update(float deltaTime) {
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			paused = !paused;
		}

		helper.update(deltaTime);
		helper.applyTo(camera);
		camera.update();

		if (!paused) {
			handleDebugInput(deltaTime);
			level.update(deltaTime);

		}
	}

	@Override
	public void render(float delta) {
		update(delta);

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		level.render(batch);
		batch.end();
	}

	private void moveCamera(float x, float y) {
		x += helper.getPosition().x;
		y += helper.getPosition().y;
		helper.setPosition(x, y);
	}

	private void handleDebugInput(float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop)
			return;

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		// Camera Controls (move)
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camMoveSpeed *= camMoveSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			moveCamera(-camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			moveCamera(camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.UP))
			moveCamera(0, camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.DOWN))
			moveCamera(0, -camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
			helper.setPosition(0, 0);

		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA))
			helper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD))
			helper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH))
			helper.setZoom(1);
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / (float) height)
				* (float) width;
		camera.update();
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
		paused = true;
	}

	@Override
	public void resume() {
		paused = false;
	}

	@Override
	public void dispose() {
		batch.dispose();
		assetManager.dispose();
		Assets.instance.dispose();

	}

}