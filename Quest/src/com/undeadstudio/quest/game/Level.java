package com.undeadstudio.quest.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.undeadstudio.quest.Constants;
import com.undeadstudio.quest.entities.AbstractEntity;
import com.undeadstudio.quest.entities.player.Player;
import com.undeadstudio.quest.net.NetClient;
import com.undeadstudio.quest.util.CameraHelper;

public class Level {

	float unitScale = 1 / 32f;
	TiledMap map;
	OrthogonalTiledMapRenderer renderer;
	SpriteBatch batch;
	OrthographicCamera camera;
	CameraHelper cameraHelper;

	public Player player;
	NetClient client;

	Array<AbstractEntity> entities = new Array<AbstractEntity>();

	boolean paused = false;

	public Level(String filename) {
		init(filename);

	}

	public void init(String filename) {

		Assets.instance.init(new AssetManager());

		map = new TmxMapLoader().load("levels/" + filename);
		renderer = new OrthogonalTiledMapRenderer(map, unitScale);
		batch = new SpriteBatch();

		float ratio = Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,
				Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		cameraHelper = new CameraHelper();
		renderer.setView(camera);
		batch.setProjectionMatrix(camera.combined);

		addEntities();

		client = new NetClient(this);

	}

	public void addEntities() {

		player = new Player(0, 0);

		entities.add(player);

	}

	public void show() {
		// TODO Auto-generated method stub

	}

	public void hide() {
		// TODO Auto-generated method stub

	}

	public void update(float deltaTime) {
		if (Gdx.input.isKeyPressed(Keys.SPACE))
			paused = !paused;

		if (!paused) {
			for (AbstractEntity entity : entities) {
				if (entity != null)
					entity.update(deltaTime);
			}

			client.update();

			handleDebugInput(deltaTime);
			handleGameInput(deltaTime);
			cameraHelper.update(deltaTime);
			cameraHelper.applyTo(camera);
			camera.update();
		}
	}

	private void moveCamera(float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}

	private void handleGameInput(float deltaTime) {
		int moveSpeed = 2;
		if (Gdx.input.isKeyPressed(Keys.W))
			player.move(0, moveSpeed * deltaTime);

		if (Gdx.input.isKeyPressed(Keys.A))
			player.move(-moveSpeed * deltaTime, 0);
		if (Gdx.input.isKeyPressed(Keys.S))
			player.move(0, -moveSpeed * deltaTime);
		if (Gdx.input.isKeyPressed(Keys.D))
			player.move(moveSpeed * deltaTime, 0);
		if (Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
			moveSpeed *= 2;
		else
			moveSpeed = 2;

	}

	private void handleDebugInput(float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop)
			return;

		if (Gdx.input.isKeyPressed(Keys.ENTER))
			cameraHelper.setTarget(cameraHelper.hasTarget() ? null : player);

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
			cameraHelper.setPosition(0, 0);

		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA))
			cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD))
			cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH))
			cameraHelper.setZoom(1);
	}

	public void render(float deltaTime) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		renderer.setView(camera);
		renderer.render();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		for (AbstractEntity entity : entities) {
			if (entity != null)
				entity.render(batch);
		}
		client.render(batch);

		batch.end();

	}

	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / (float) height)
				* (float) width;
		camera.update();
	}

	public void pause() {
		// TODO Auto-generated method stub

	}

	public void resume() {
		// TODO Auto-generated method stub

	}

	public void dispose() {
		renderer.dispose();
	}
}
