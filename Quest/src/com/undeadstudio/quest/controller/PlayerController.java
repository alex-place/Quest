package com.undeadstudio.quest.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.undeadstudio.quest.entities.Player;
import com.undeadstudio.quest.map.Level;

public class PlayerController implements InputProcessor {

	Player player;

	public PlayerController(Player player) {
		this.player = player;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		Level.instance.moveMonsters();

		switch (keycode) {
		case Keys.W:
			Level.instance.move(player, 0, 1);
			break;
		case Keys.S:
			Level.instance.move(player, 0, -1);
			break;
		case Keys.A:
			Level.instance.move(player, -1, 0);
			break;
		case Keys.D:
			Level.instance.move(player, 1, 0);
			break;

		default:
			break;
		}

		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
