package com.undeadstudio.quest.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.undeadstudio.quest.entities.AbstractCharacter.DIRECTION;
import com.undeadstudio.quest.entities.AbstractEntity;
import com.undeadstudio.quest.entities.CellEntity;
import com.undeadstudio.quest.entities.TestCharacter;
import com.undeadstudio.quest.entities.player.Player;
import com.undeadstudio.quest.headsupdisplay.HeadsUpDisplay;
import com.undeadstudio.quest.interactions.InteractionChecker;
import com.undeadstudio.quest.net.NetClient;
import com.undeadstudio.quest.util.Assets;
import com.undeadstudio.quest.util.CameraHelper;
import com.undeadstudio.quest.util.Constants;

public class Level extends InputAdapter {

	float unitScale = 1 / 32f;
	TiledMap map;
	TiledMapTileLayer collisionLayer;
	OrthogonalTiledMapRenderer renderer;
	SpriteBatch batch;
	OrthographicCamera camera;
	CameraHelper cameraHelper;
	ShapeRenderer shapeRenderer;
	HeadsUpDisplay hud;

	public Player player;
	public InteractionChecker interactionChecker;
	public NetClient client;

	public Array<AbstractEntity> entities = new Array<AbstractEntity>();
	public Array<AbstractEntity> npcEntities = new Array<AbstractEntity>();

	public Array<CellEntity> cells = new Array<CellEntity>();
	boolean paused = false;
	TestCharacter character;

	String complex;

	public Level(String filename) {
		init(filename);

	}

	public void init(String filename) {

		Gdx.input.setInputProcessor(this);

		Assets.instance.init(new AssetManager());
		batch = new SpriteBatch();

		map = new TmxMapLoader().load("levels/" + filename);
		collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
		renderer = new OrthogonalTiledMapRenderer(map, unitScale, batch);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setColor(Color.RED);

		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH,
				Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		cameraHelper = new CameraHelper();
		renderer.setView(camera);
		batch.setProjectionMatrix(camera.combined);
		shapeRenderer.setProjectionMatrix(camera.combined);
		hud = new HeadsUpDisplay(this);

		addEntities();
		// initCells();

		// interactionChecker = new InteractionChecker(this);

		client = new NetClient(this);

	}

	public void addEntities() {

		player = new Player(0, 0);

		character = new TestCharacter(2, 2);

		entities.add(player);

		entities.add(character);
		npcEntities.add(character);

		cameraHelper.setTarget(player);

	}

	public void show() {

	}

	public void hide() {

	}

	public void update(float deltaTime) {
		if (Gdx.input.isKeyPressed(Keys.SPACE))
			paused = !paused;

		if (!paused) {
			for (AbstractEntity entity : entities) {
				if (entity != null)
					entity.update(deltaTime);
			}

			client.update(deltaTime);

			handleDebugInput(deltaTime);
			handleGameInput(deltaTime);

			// interactionChecker.checkForInteractionsWithPlayer(player,
			// player.direction);

			cameraHelper.update(deltaTime);
			cameraHelper.applyTo(camera);
			camera.update();

			renderer.setView(camera);
			batch.setProjectionMatrix(camera.combined);
			shapeRenderer.setProjectionMatrix(camera.combined);
		}

	}

	private void moveCamera(float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}

	private void handleGameInput(float deltaTime) {
		int moveSpeed = 2;

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		if (Gdx.input.isKeyPressed(Keys.SHIFT_RIGHT))
			moveSpeed *= 2;

		if (Gdx.input.isKeyPressed(Keys.W)) {
			player.direction = DIRECTION.UP;
			player.move(0, moveSpeed * deltaTime);
		}
		if (Gdx.input.isKeyPressed(Keys.A)) {
			player.direction = DIRECTION.LEFT;
			player.move(-moveSpeed * deltaTime, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			player.direction = DIRECTION.DOWN;
			player.move(0, -moveSpeed * deltaTime);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			player.move(moveSpeed * deltaTime, 0);
			player.direction = DIRECTION.RIGHT;
		}

	}

	private void handleDebugInput(float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop)
			return;

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

	@Override
	public boolean keyUp(int keycode) {

		switch (keycode) {
		case Keys.ENTER:
			cameraHelper.setTarget(cameraHelper.hasTarget() ? null : player);

			break;
		}

		// if (Gdx.input.isKeyPressed(Keys.ENTER))

		return false;

	}

	public void render(float deltaTime) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		renderer.setView(camera);
		renderer.render();

		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.rect(player.bounds.x, player.bounds.y,
				player.bounds.width, player.bounds.height);

		for (AbstractEntity entity : entities) {
			Rectangle rec = entity.bounds;
			shapeRenderer.rect(rec.x, rec.y, rec.width, rec.height);

		}

		for (Rectangle rec : NetClient.boundingBoxes) {
			shapeRenderer.rect(rec.x, rec.y, rec.width, rec.height);
		}

		for (Rectangle rec : player.boundingBoxes) {
			if (rec != null)
				shapeRenderer.rect(rec.x, rec.y, rec.width, rec.height);
		}

		for (CellEntity cell : cells) {
			Rectangle rec = cell.bounds;
			shapeRenderer.rect(rec.x, rec.y, rec.width, rec.height);
		}

		shapeRenderer.rect(character.bounds.x, character.bounds.y,
				character.bounds.width, character.bounds.height);

		shapeRenderer.end();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		for (AbstractEntity entity : entities) {
			if (entity != null)
				entity.render(batch);
		}
		client.render(batch);

		batch.end();

		batch.setProjectionMatrix(hud.camera.combined);
		batch.begin();
		hud.render(batch);
		batch.end();

	}

	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / (float) height)
				* (float) width;
		camera.update();

		hud.resize(width, height);
	}

	public void pause() {
		// TODO Auto-generated method stub

	}

	public void resume() {
		// TODO Auto-generated method stub

	}

	public void dispose() {
		renderer.dispose();
		batch.dispose();
		map.dispose();
		shapeRenderer.dispose();

	}
}
