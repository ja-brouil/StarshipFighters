package com.jb.gamestates;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jb.input.GameInputProcessor;
import com.jb.main.Game;


public abstract class GameState {

	protected GameStateManager gsm;
	protected Game game;
	
	protected SpriteBatch spriteBatch;
	protected OrthographicCamera cam;
	protected GameInputProcessor input;
	
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
		game = gsm.getGame();
		spriteBatch = game.getSpriteBatch();
		cam = game.getCamera();
		input = gsm.getInput();
	}
	
	public abstract void init();
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render();
	public abstract void dispose();

}
