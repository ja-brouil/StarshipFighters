package com.jb.gamestates;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jb.input.GameInputProcessor;
import com.jb.main.Game;


public abstract class GameState {

	// GameState and Game Objects
	protected GameStateManager gsm;
	protected Game game;
	
	// Spritebatch and camera
	protected SpriteBatch spriteBatch;
	protected OrthographicCamera cam;
	protected GameInputProcessor input;
	
	// Asset Manager
	protected AssetManager assetManager;
	
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
		game = gsm.getGame();
		spriteBatch = game.getSpriteBatch();
		cam = game.getCamera();
		input = gsm.getInput();
		assetManager = game.getAssetManager();
	}
	
	// Methods
	public abstract void init();
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render();
	public abstract void dispose();

}
