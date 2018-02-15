package com.jb.gamestates;

import java.util.Stack;
import com.jb.input.GameInputProcessor;
import com.jb.main.Game;

public class GameStateManager {

	// Game Object
	private Game game;
	
	// Game States
	private Stack<GameState> gameStates;
	
	// Game Input
	private GameInputProcessor input;
	
	public GameStateManager(Game game) {
		this.game = game;
		gameStates = new Stack<GameState>();
		pushState(new MenuState(this)); 
		input = game.getInput();
	}
	
	// Update Method (peek checks top of stack without removing it)
	public void update(float dt) {
		gameStates.peek().update(dt); 
	}
	
	// Render
	public void render() {
		gameStates.peek().render();
	}
	
	// Get State
	private GameState getState(GameState gameState) {
		return gameState;
	}
	
	// Set State of the Game (pause, play, etc...) Replace at the top of the stack
	// This will depose of the state first
	public void setState(GameState gameState) {
		popState();
		pushState(gameState);
	}

	
	// Push new state to the top of the stack
	public void pushState(GameState gameState) {
		gameStates.push(getState(gameState));
	}
	
	// Dispose of the top of the stack
	public void popState(){
		GameState gameState = gameStates.pop();
		gameState.dispose();
		System.gc();
	}
	
	// Setters + Getters
	public Game getGame() {
		return game;
	}
	
	// Get Input
	public GameInputProcessor getInput() {
		return input;
	}
	

}
