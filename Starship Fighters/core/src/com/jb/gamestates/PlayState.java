package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.jb.HUD.HealthBar;
import com.jb.gameobjects.GameObjects;
import com.jb.gameobjects.enemies.Explosion;
import com.jb.gameobjects.enemies.level1enemies.EnemyBullets;
import com.jb.gameobjects.items.Item;
import com.jb.gameobjects.player.Player;
import com.jb.gamestates.Transition.TransitionType;
import com.jb.input.GameKeys;
import com.jb.level.LevelManager;

public class PlayState extends GameState {

	// Player
	private Player player;
	
	// All Enemies
	private Array<GameObjects> allEnemies;
	private Array<EnemyBullets> enemyBulletList;
	
	// Explosion
	private Array<Explosion> explosionList;
	
	// All Items
	private Array<Item> allItems;
	
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
		assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		init();
	}

	@Override
	public void init() {

		// Start Player
		player = new Player(300, 150, 0, 0, assetManager, this);
		
		// Start HUD
		healthBar = new HealthBar(this, 1, 1, 0 ,0);
		
		// Start Enemy Array
		allEnemies = new Array<GameObjects>();
		
		// Start all Items
		allItems = new Array<Item>();
		
		// Start Enemy Bullet List
		enemyBulletList = new Array<EnemyBullets>();
		
		// Start Explosion List
		explosionList = new Array<Explosion>();
		
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

		// Check for Game Over
		if (player.isDead()) {
			levelManager.getCurrentLevel().getLevelMusic().stop();
			gsm.setState(new Transition(gsm, this, new GameOverState(gsm), TransitionType.BLACK_FADE));
		}
	}

	@Override
	public void render() {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Play State Draw
		spriteBatch.setProjectionMatrix(cam.combined);
		
		// Level Draw
		levelManager.render();
		
		// Player and HUD Objects
		healthBar.render(spriteBatch);

		// Player Render
		player.draw(spriteBatch);
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
	
	public Array<GameObjects> getAllEnemies(){
		return allEnemies;
	}
	
	public Array<Item> getAllItems(){
		return allItems;
	}
	
	public Array<EnemyBullets> getEnemyBulletList(){
		return enemyBulletList;
	}
	
	public Array<Explosion> getExplosionList(){
		return explosionList;
	}
	
	public GameStateManager getGSM() {
		return gsm;
	}
}
