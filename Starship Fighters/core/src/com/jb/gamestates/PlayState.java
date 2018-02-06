package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jb.gameobjects.player.Player;
import com.jb.input.GameKeys;
import com.jb.level.LevelManager;
import com.jb.level.level1.Level1;

public class PlayState extends GameState {

	// Player
	private Player player;
	
	// Asset Manager
	private AssetManager assetManager;

	// GamePlay
	private boolean inputAllowed = true;

	// Level Manager
	private LevelManager levelManager;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		
		// Start Asset Manager
		assetManager = game.getAssetManager();
		
		init();
	}

	@Override
	public void init() {

		// Start Player
		player = new Player(300, 150, 0, 0, assetManager);

		// Start Level
		levelManager = new LevelManager(this);
	}

	@Override
	public void handleInput() {

		// Escape to quit to exit faster. Remember to remove this later!
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		// Set Game Keys
		if (inputAllowed) {
			player.getPlayerInput().setLeft(GameKeys.isDown(GameKeys.LEFT));
			player.getPlayerInput().setRight(GameKeys.isDown(GameKeys.RIGHT));
			player.getPlayerInput().setUp(GameKeys.isDown(GameKeys.UP));
			player.getPlayerInput().setDown(GameKeys.isDown(GameKeys.DOWN));
			player.getPlayerInput().setShoot(GameKeys.isDown(GameKeys.SPACE));
			player.getPlayerInput().setMissile(GameKeys.isPressed(GameKeys.SHIFT));
		}

		// Update Keys
		GameKeys.update();
	}

	@Override
	public void update(float dt) {

		
		// Check Input
		handleInput();
		
		// Update Player
		player.update(dt);

		// Update Level
		levelManager.update(dt);
	}

	@Override
	public void render() {
		// NOTICE: ORDER HERE IS IMPORTANT. THINK OF THIS AS LAYERS

		/* Clear screen to Black Background
		 To use different color scales: modify the openGL state with c++
		 Uncomment to clear screen with white. Can mess up transition states
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); */

		// Play State Draw
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();

		// Level Render
		levelManager.render();

		// Player Render
		player.draw(spriteBatch);
		
		// Close SpriteBatch and Shape Renderer
		spriteBatch.end();
	}

	@Override
	public void dispose() {}
	
	public LevelManager getLevelManager() {
		return levelManager;
	}

	public Player getPlayer() {
		return player;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

}
