package com.jb.handler;

import java.util.Stack;

import com.jb.gamestates.GameState;
import com.jb.gamestates.PlayState;
import com.jb.input.GameInputProcessor;
import com.jb.main.Game;

public class GameStateManager {

	private Game game;
	private Stack<GameState> gameStates;
	public static final int PLAY = 100;
	private GameInputProcessor input;
	
	public GameStateManager(Game game) {
		this.game = game;
		gameStates = new Stack<GameState>();
		pushState(PLAY); // Push Play state right away
		input = game.getInput();
	}
	
	public void update(float dt) {
		gameStates.peek().update(dt); // check top of the stack without removing it from the list
	}
	
	public void render() {
		gameStates.peek().render();
	}
	
	// Get Play State
	private GameState getState(int state) {
		if (state == PLAY) {
			return new PlayState(this);
		}
		
		return null;
	}
	
	// Set State of the Game (pause, play, etc...) Replace at the top of the stack
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
		
	}
	
	
	public Game getGame() {
		return game;
	}
	
	public GameInputProcessor getInput() {
		return input;
	}
}
