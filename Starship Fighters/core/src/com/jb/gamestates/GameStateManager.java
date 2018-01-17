package com.jb.gamestates;

import java.util.Stack;

import com.badlogic.gdx.audio.Music;
import com.jb.assetmanagers.audio.MusicManager;
import com.jb.assetmanagers.audio.SoundManager;
import com.jb.input.GameInputProcessor;
import com.jb.main.Game;

public class GameStateManager {

	// Game Asset
	private Game game;
	
	// Game States
	private Stack<GameState> gameStates;
	public static final int INTRO = 2;
	public static final int PLAY = 1;
	public static final int MENU = 0;
	public static final int GAMEOVER = 3;
	public static final int VICTORY = 4;
	
	// Game Processor
	private GameInputProcessor input;
	
	// Music | Sound
	private MusicManager musicManager;
	private SoundManager soundManager;
	
	public GameStateManager(Game game) {
		this.game = game;
		gameStates = new Stack<GameState>();
		pushState(MENU); 
		input = game.getInput();
		musicManager = game.getMusicManager();
		soundManager = game.getSoundManager();
	}
	
	public void update(float dt) {
		gameStates.peek().update(dt); // check top of the stack without removing it from the list
	}
	
	public void render() {
		gameStates.peek().render();
	}
	
	// Get Play State
	private GameState getState(int state) {
		
		if (state == MENU) {
			return new MenuState(this);
		}
		
		if (state == PLAY) {
			return new PlayState(this);
		}
		
		if (state == INTRO) {
			return new IntroState(this);
		}
		
		if (state == GAMEOVER) {
			return new GameOverState(this);
		}
		
		if (state == VICTORY) {
			return new VictoryState(this);
		}
		
		return null;
	}
	
	// Set State of the Game (pause, play, etc...) Replace at the top of the stack
	// This will depose of the state first
	public void setState(int state) {
		popState();
		pushState(state);
	}
	
	// Push new state to the top of the stack
	public void pushState(int state) {
		gameStates.push(getState(state));
	}
	
	// Dispose of the top of the stack
	public void popState(){
		GameState g = gameStates.pop();
		g.dispose();
		System.gc();
	}
	
	
	public Game getGame() {
		return game;
	}
	
	public GameInputProcessor getInput() {
		return input;
	}
}
