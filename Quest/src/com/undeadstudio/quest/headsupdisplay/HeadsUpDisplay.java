package com.undeadstudio.quest.headsupdisplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.undeadstudio.quest.map.Level;
import com.undeadstudio.quest.util.Assets;
import com.undeadstudio.quest.util.Constants;

public class HeadsUpDisplay {

	public OrthographicCamera camera;


	public HeadsUpDisplay() {
		init();
	}

	private void init() {
		camera = new OrthographicCamera(Constants.VIEWPORT_HUD_WIDTH,
				Constants.VIEWPORT_HUD_HEIGHT);

		camera.position.set(0, 0, 0);
		camera.setToOrtho(true); // flip y-axis
		camera.update();

	}

	public void render(SpriteBatch batch) {
		renderFPSCounter(batch);
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

	public void update() {

	}

	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HUD_HEIGHT / (float) height)
				* (float) width;
		camera.position.set(camera.viewportWidth / 2,
				camera.viewportHeight / 2, 0);
		camera.update();
	}

}
