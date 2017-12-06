package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.jb.gameobjects.player.Player;
import com.jb.gameobjects.player.PlayerBullets;
import com.jb.handler.GameStateManager;
import com.jb.input.GameKeys;

public class PlayState extends GameState{
	
	private Player player;
	private Array<PlayerBullets> shipBullets;
	private PlayerBullets playerBullets;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		init();
		
	}
	
	@Override
	public void init() {
		player = new Player(300, 150, 0, 0);
		playerBullets = new PlayerBullets(player.getX(), player.getY(), 0, 10);
	}

	@Override
	public void handleInput() {
		
		// Escape to quit to exit faster. Remember to remove this later!
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
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
		playerBullets.update(dt);
		
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
		playerBullets.draw(spriteBatch);
		spriteBatch.end();
		
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		
	}
	
}
