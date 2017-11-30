package com.jb.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class GameInputProcessor extends InputAdapter {

	// Processor for Keyboard Processing
	public boolean keyDown(int key) {

		// Up
		if (key == Keys.UP) {
			GameKeys.setKey(GameKeys.UP, true);
		}

		// Down
		if (key == Keys.DOWN) {
			GameKeys.setKey(GameKeys.DOWN, true);
		}

		// Left
		if (key == Keys.LEFT) {
			GameKeys.setKey(GameKeys.LEFT, true);
		}

		// Right
		if (key == Keys.RIGHT) {
			GameKeys.setKey(GameKeys.RIGHT, true);
		}

		// Enter
		if (key == Keys.ENTER) {
			GameKeys.setKey(GameKeys.ENTER, true);
		}

		// Escape
		if (key == Keys.ESCAPE) {
			GameKeys.setKey(GameKeys.ESCAPE, true);
		}

		// Spacebar
		if (key == Keys.SPACE) {
			GameKeys.setKey(GameKeys.SPACE, true);
		}

		// Left Shift
		if (key == Keys.SHIFT_LEFT || key == Keys.SHIFT_RIGHT) {
			GameKeys.setKey(GameKeys.SHIFT, true);
		}

		return true;
	}

	public boolean keyUp(int key) {

		// Up
		if (key == Keys.UP) {
			GameKeys.setKey(GameKeys.UP, false);
		}

		// Down
		if (key == Keys.DOWN) {
			GameKeys.setKey(GameKeys.DOWN, false);
		}

		// Left
		if (key == Keys.LEFT) {
			GameKeys.setKey(GameKeys.LEFT, false);
		}

		// Right
		if (key == Keys.RIGHT) {
			GameKeys.setKey(GameKeys.RIGHT, false);
		}

		// Enter
		if (key == Keys.ENTER) {
			GameKeys.setKey(GameKeys.ENTER, false);
		}

		// Escape
		if (key == Keys.ESCAPE) {
			GameKeys.setKey(GameKeys.ESCAPE, false);
		}

		// Spacebar
		if (key == Keys.SPACE) {
			GameKeys.setKey(GameKeys.SPACE, false);
		}

		// Left Shift
		if (key == Keys.SHIFT_LEFT || key == Keys.SHIFT_RIGHT) {
			GameKeys.setKey(GameKeys.SHIFT, false);
		}

		return true;
	}

}
