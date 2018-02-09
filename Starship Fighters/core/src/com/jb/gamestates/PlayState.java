package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jb.HUD.HealthBar;
import com.jb.gameobjects.player.Player;
import com.jb.input.GameKeys;
import com.jb.level.LevelManager;

public class PlayState extends GameState {

	// Player
	private Player player;
	
	// HUD
	private HealthBar healthBar;
	
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
		
		// Start HUD
		healthBar = new HealthBar(this, 1, 1);

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
			setPlayerInput();
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
		
		// Update HUD
		healthBar.update(dt);

		// Update Level
		levelManager.update(dt);
	}

	@Override
	public void render() {
		
		// Play State Draw
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();

		// Level Render
		levelManager.render();
		
		// Draw HUD
		healthBar.render(spriteBatch);

		// Player Render
		player.draw(spriteBatch);
		
		// Close SpriteBatch and Shape Renderer
		spriteBatch.end();
	}

	@Override
	public void dispose() {}
	// Helper Methods
	// Set Player Input
	private void setPlayerInput() {
		player.getPlayerInput().setLeft(GameKeys.isDown(GameKeys.LEFT));
		player.getPlayerInput().setRight(GameKeys.isDown(GameKeys.RIGHT));
		player.getPlayerInput().setUp(GameKeys.isDown(GameKeys.UP));
		player.getPlayerInput().setDown(GameKeys.isDown(GameKeys.DOWN));
		player.getPlayerInput().setShoot(GameKeys.isDown(GameKeys.SPACE));
		player.getPlayerInput().setMissile(GameKeys.isPressed(GameKeys.SHIFT));
	}
	
	// Getters
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
