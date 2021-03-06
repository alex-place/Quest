package com.undeadstudio.quest.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class ScrollTestScreen implements Screen {
	private Stage stage;

	private static final String reallyLongString = "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
			+ "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n"
			+ "This\nIs\nA\nReally\nLong\nString\nThat\nHas\nLots\nOf\nLines\nAnd\nRepeats.\n";

	@Override
	public void render(float delta) {
		this.stage.act();
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		this.stage.draw();
	}

	@Override
	public void resize(final int width, final int height) {
		this.stage = new Stage();
		Gdx.input.setInputProcessor(this.stage);
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

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

}
