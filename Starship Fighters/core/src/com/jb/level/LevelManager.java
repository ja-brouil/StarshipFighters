package com.jb.level;

import java.util.Stack;

import com.jb.gamestates.PlayState;
import com.jb.level.level1.Level1;

public class LevelManager {
	
	// PlayState
	private PlayState playState;
	
	// Level Stack
	private Stack<Level> worldLevels;

	public LevelManager(PlayState playState) {
		this.playState = playState;
		// Level Stack
		worldLevels = new Stack<Level>();
		
		// Start Level 1
		pushLevel(new Level1(playState, playState.getAssetManager()));
	}
	
	// Update Level
	public void update(float dt) {
		worldLevels.peek().update(dt);
	}
	
	// Render Level
	public void render() {
		worldLevels.peek().render(playState.getSpriteBatch());
	}
	
	// Get Level
	private Level getLevel(Level level) {
		return level;
	}
	
	// Set Level
	public void setLevel(Level level) {
		popLevel();
		pushLevel(level);
	}
	
	// Push new level
	public void pushLevel(Level level) {
		worldLevels.push(getLevel(level));
	}
	
	// Dispose of the level
	public void popLevel() {
		worldLevels.pop();
		System.gc();
	}

}
