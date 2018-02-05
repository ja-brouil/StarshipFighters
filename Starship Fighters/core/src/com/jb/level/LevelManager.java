package com.jb.level;

import java.util.Stack;

import com.jb.gamestates.PlayState;
import com.jb.level.level1.Level1;

public class LevelManager {
	
	// PlayState
	private PlayState playState;
	
	// Levels
	private Stack<Level> worldLevels;
	public static final int LEVEL1 = 1;
	public static final int LEVEL2 = 2;

	public LevelManager(PlayState playState) {
		this.playState = playState;
	}
	
	// Update Level
	public void update(float dt) {
		worldLevels.peek().update(dt);
	}
	
	// Render Level
	public void render() {
		worldLevels.peek().render(playState.getGSM().getGame().getSpriteBatch());
	}
	
	// Get Level
	private Level getLevel(int level) {
		// Change Level
		
		
		return null;
	}
	
	// Set Level
	public void setLevel(int level) {
		popLevel();
		pushLevel(level);
	}
	
	// Push new level
	public void pushLevel(int level) {
		worldLevels.push(getLevel(level));
	}
	
	// Dipose of the level
	public void popLevel() {
		worldLevels.pop();
		System.gc();
	}

}
