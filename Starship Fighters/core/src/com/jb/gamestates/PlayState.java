package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.jb.gameobjects.player.Player;
import com.jb.gamestates.levels.level1.Level1;
import com.jb.input.GameKeys;

public class PlayState extends GameState {

	// Player
	private Player player;
	
	// Asset Manager
	private AssetManager assetManager;

	// GamePlay
	private boolean inputAllowed = true;

	// Level Objects
	private Level1 level1;


	public PlayState(GameStateManager gsm) {
		super(gsm);
		
		// Start Asset Manager
		assetManager = game.getAssetManager();
		
		init();

	}

	@Override
	public void init() {

		// Start Player
		player = new Player(300, 150, 0, 0, this, assetManager);

		// Start Level
		level1 = new Level1("data/levels/testmap.tmx", this, assetManager);
		
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
		level1.update(dt);

	}


	@Override
	public void render() {
		// NOTICE: ORDER HERE IS IMPORTANT. THINK OF THIS AS LAYERS

		// Clear screen to Black Background
		// To use different color scales: modify the openGL state with c++
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Play State Draw
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();

		// Level Render
		level1.render(spriteBatch);

		// Player Render
		player.draw(spriteBatch);
		
		// Close SpriteBatch and Shape Renderer
		spriteBatch.end();

	}

	@Override
	public void dispose() {}
	
	public Level1 getLevel1() {
		return level1;
	}

	public GameStateManager getGSM() {
		return gsm;
	}

	public Player getPlayer() {
		return player;
	}



}
