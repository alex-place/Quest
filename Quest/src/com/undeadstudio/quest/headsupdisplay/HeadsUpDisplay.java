package com.undeadstudio.quest.headsupdisplay;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.esotericsoftware.tablelayout.Cell;
import com.undeadstudio.quest.QuestMain;
import com.undeadstudio.quest.map.Level;
import com.undeadstudio.quest.util.Assets;
import com.undeadstudio.quest.util.Constants;

public class HeadsUpDisplay {

	public OrthographicCamera camera;

	final Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

	// For the chat log
	final Table scrollTable = new Table();
	private ScrollPane scroller;
	final Table table = new Table();

	public static Color chatColor = Color.WHITE;
	public static String chatMessage = "";
	public static Stage stage;

	// For the fps counter
	int fps = Gdx.graphics.getFramesPerSecond();
	Label fpsLabel = new Label("FPS : " + fps, skin);

	private HeadsUpDisplay() {
		init();
	}

	public static HeadsUpDisplay instance = new HeadsUpDisplay();

	private void init() {
		// 480px * 320px
		camera = new OrthographicCamera(Constants.VIEWPORT_HUD_WIDTH,
				Constants.VIEWPORT_HUD_HEIGHT);

		camera.position.set(0, 0, 0);
		camera.setToOrtho(true); // flip y-axis
		camera.update();

		initChatLog();
		initFPSCounter();

	}

	public void initChatLog() {
		stage = new Stage(Constants.VIEWPORT_HUD_WIDTH,
				Constants.VIEWPORT_HUD_HEIGHT);
		Level.instance.input.addProcessor(stage);

		scroller = new ScrollPane(scrollTable);

		table.setSize(Constants.CHATLOG_WIDTH, Constants.CHATLOG_HEIGHT);
		table.setPosition(0, 0);
		table.padBottom(20);
		table.add(scroller).fill().expand();

		HeadsUpDisplay.stage.addActor(table);
	}

	public void addMessage(String message, Color color) {
		Label label = new Label(message, skin);
		label.setWrap(true);
		label.setColor(color);
		label.setFontScale(0.5f);
		scrollTable.add(label).width(Constants.CHATLOG_WIDTH);
		scrollTable.row();
	}

	public void render(SpriteBatch batch) {
		scroller.setScrollPercentY(1);
		renderStage();
		Table.drawDebug(stage);

		renderFPSCounter();

	}

	public void renderStage() {
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
	}

	private void initFPSCounter() {
		float x = camera.viewportWidth - 55;
		float y = camera.viewportHeight - 15;

		fpsLabel.setPosition(50, 50);

		

		stage.addActor(fpsLabel);

	}

	public void renderFPSCounter() {
		fps = Gdx.graphics.getFramesPerSecond();
		if (fps >= 45) {
			// 45 or more FPS show up in green
			fpsLabel.setColor(0, 1, 0, 1);
		} else if (fps >= 30) {
			// 30 or more FPS show up in yellow
			fpsLabel.setColor(1, 1, 0, 1);
		} else {
			// less than 30 FPS show up in red
			fpsLabel.setColor(1, 0, 0, 1);
		}
		fpsLabel.setText("FPS: " + fps);
		// Label fpsLabel = new Label("FPS : " + fps, skin);
	}

	private void renderVersion(SpriteBatch batch) {
		float x = 0;
		float y = camera.viewportHeight - 15;
		BitmapFont versionFont = Assets.instance.fonts.defaultSmall;

		versionFont.setColor(1, 1, 1, 1); // white

		versionFont.draw(batch, QuestMain.NAME, x, y);

	}

	private void renderSimpleChat(SpriteBatch batch) {
		float x = 0;
		float y = camera.viewportHeight - 30;
		BitmapFont chatFont = Assets.instance.fonts.defaultSmall;

		chatFont.setColor(0, 1, 0, 1); // green

		chatFont.draw(batch, chatMessage, x, y);

	}

	public void update() {

	}

	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HUD_HEIGHT / (float) height)
				* (float) width;
		camera.position.set(camera.viewportWidth / 2,
				camera.viewportHeight / 2, 0);
		camera.update();

		stage.setViewport(Constants.VIEWPORT_HUD_WIDTH,
				Constants.VIEWPORT_HEIGHT, true);
		if (table != null) {
			table.setSize(Constants.VIEWPORT_HUD_WIDTH,
					Constants.VIEWPORT_HUD_HEIGHT);
			table.bottom().left();
			scrollTable.bottom().left();

		}

	}

	public void dispose() {
	}

}
