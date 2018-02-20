package com.jb.level;

import java.util.Stack;

import com.jb.gamestates.PlayState;
import com.jb.level.level1.Level1;
import com.jb.level.level2.Level2;

public class LevelManager {
	
	// PlayState
	private PlayState playState;
	
	// Level Stack
	private Stack<Level> worldLevels;
	
	// Current Level
	private Level currentLevel;

	public LevelManager(PlayState playState) {
		this.playState = playState;
		// Level Stack
		worldLevels = new Stack<Level>();
		
		// Start Level 1
		//pushLevel(new Level1(playState, playState.getAssetManager()));
		pushLevel(new Level2(playState, playState.getAssetManager(), null));
	}
	
	// Update Level
	public void update(float dt) {
		worldLevels.peek().update(dt);
	}
	
	// Render Level
	public void render() {
		worldLevels.peek().render(playState.getSpriteBatch());
	}
	
	// Set Level
	public void setLevel(Level level) {
		popLevel();
		pushLevel(level);
	}
	
	// Push new level
	public void pushLevel(Level level) {
		currentLevel = level;
		worldLevels.push(level);
	}
	
	// Dispose of the level
	public void popLevel() {
		worldLevels.pop();
		System.gc();
	}
	
	// Get Level
	public Level getCurrentLevel() {
		return currentLevel;
	}
}
