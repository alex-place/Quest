package com.undeadstudio.quest.headsupdisplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.undeadstudio.quest.QuestMain;
import com.undeadstudio.quest.map.Level;
import com.undeadstudio.quest.util.Assets;
import com.undeadstudio.quest.util.Constants;

public class HeadsUpDisplay {

	public OrthographicCamera camera;

	// For the chat log
	public static Color chatColor = Color.WHITE;
	public static String chatMessage = "";
	public static Stage stage;

	private static final String reallyLongString = "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
			+ "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
			+ "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n";

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

		stage = new Stage(Constants.VIEWPORT_HUD_WIDTH,
				Constants.VIEWPORT_HUD_HEIGHT);
		Level.instance.input.addProcessor(stage);

		final Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		final Label text = new Label(reallyLongString, skin);
		text.setAlignment(Align.center);
		text.setWrap(true);
		final Label text2 = new Label("This is a short string!", skin);
		text2.setAlignment(Align.center);
		text2.setWrap(true);
		final Label text3 = new Label(reallyLongString, skin);
		text3.setAlignment(Align.center);
		text3.setWrap(true);

		final Table scrollTable = new Table();
		scrollTable.add(text);
		scrollTable.row();
		scrollTable.add(text2);
		scrollTable.row();
		scrollTable.add(text3);

		final ScrollPane scroller = new ScrollPane(scrollTable);

		final Table table = new Table();
		table.setSize(150, 100);
		table.padBottom(20);
		table.defaults().prefSize(200, 100);
		table.add(scroller).fill().expand();

		this.stage.addActor(table);

	}

	public void render(SpriteBatch batch) {
		renderFPSCounter(batch);
		renderVersion(batch);
		renderSimpleChat(batch);
		renderStage();

	}

	public void renderStage() {
		stage.act();
		stage.draw();
	}

	private void renderFPSCounter(SpriteBatch batch) {
		float x = camera.viewportWidth - 55;
		float y = camera.viewportHeight - 15;
		int fps = Gdx.graphics.getFramesPerSecond();
		BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
		if (fps >= 45) {
			// 45 or more FPS show up in green
			fpsFont.setColor(0, 1, 0, 1);
		} else if (fps >= 30) {
			// 30 or more FPS show up in yellow
			fpsFont.setColor(1, 1, 0, 1);
		} else {
			// less than 30 FPS show up in red
			fpsFont.setColor(1, 0, 0, 1);
		}

		fpsFont.draw(batch, "FPS: " + fps, x, y);
		fpsFont.setColor(1, 1, 1, 1); // white

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

		// Gdx.input.setInputProcessor(this.stage);
		final Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		final Label text = new Label(reallyLongString, skin);
		text.setAlignment(Align.center);
		text.setWrap(true);
		final Label text2 = new Label("This is a short string!", skin);
		text2.setAlignment(Align.center);
		text2.setWrap(true);
		final Label text3 = new Label(reallyLongString, skin);
		text3.setAlignment(Align.center);
		text3.setWrap(true);

		final Table scrollTable = new Table();
		scrollTable.add(text);
		scrollTable.row();
		scrollTable.add(text2);
		scrollTable.row();
		scrollTable.add(text3);

		final ScrollPane scroller = new ScrollPane(scrollTable);

		final Table table = new Table();
		table.setSize(150, 100);
		table.padBottom(20);
		table.defaults().prefSize(200, 100);
		table.add(scroller).fill().expand();

	}

	public void dispose() {
	}

}
