package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jb.gameobjects.player.Player;
import com.jb.handler.GameStateManager;
import com.jb.input.GameKeys;
import com.jb.main.Game;

public class PlayState extends GameState{
	
	private ShapeRenderer sRenderer;
	private Player player;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		init();
		
	}
	
	@Override
	public void init() {
		sRenderer = new ShapeRenderer();
		player = new Player(300, 150, 0, 0);
		
	}

	@Override
	public void handleInput() {
		
		// Escape to quit to exit faster. Remember to remove this later!
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			System.exit(0);
		}
		
		GameKeys.update();
	}

	@Override
	public void update(float dt) {
		
		// Set Game Keys
		player.setLeft(GameKeys.isDown(GameKeys.LEFT));
		player.setRight(GameKeys.isDown(GameKeys.RIGHT));
		player.setUp(GameKeys.isDown(GameKeys.UP));
		player.setDown(GameKeys.isDown(GameKeys.DOWN));
		player.setShoot(GameKeys.isDown(GameKeys.SPACE));
		player.setMissile(GameKeys.isPressed(GameKeys.SHIFT));
		
		// Check Input
		handleInput();
		
		// Update Player
		player.update(dt);
		
		// Update Level
		
		
	}

	@Override
	public void render() {
		
		// Clear screen to Black Background
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Play State Draw
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		player.draw(spriteBatch);
		spriteBatch.end();
		
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		
	}
	
}
