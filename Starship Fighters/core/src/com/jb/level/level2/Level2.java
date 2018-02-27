package com.jb.level.level2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.jb.gameobjects.GameObjects;
import com.jb.gameobjects.enemies.Explosion;
import com.jb.gameobjects.enemies.level1enemies.EnemyBullets;
import com.jb.gameobjects.items.Item;
import com.jb.gamestates.PlayState;
import com.jb.level.Level;

public class Level2 extends Level {
	
	// Enemy List
	private Array<EnemyBullets> enemyBulletList;
	private Array<GameObjects> allEnemies;
	
	// Boss
	
	// Explosion Array
	private Array<Explosion> explosionList;
	
	// Item List
	private Array<Item> energyTankList;
	
	// Collision Handling
	private Level2CollisionDetection level2CollisionDetection;
	
	// Level Flow
	private Level2GameFlow level2GameFlow;
	
	// Load Assets
	private Level2Assets level2Assets;
	
	// Boolean switches
	private boolean gameFlowStart = true;
	
	public Level2(PlayState playState, AssetManager assetManager, OrthogonalTiledMapRenderer orthogonalTiledMapRenderer) {
		super(playState, assetManager, orthogonalTiledMapRenderer);
		initialize();
	}
	
	// Initialize
	public void initialize() {
		// Load Arrays and clear them
		startArrays();
		
		// Load Level Assets
		level2Assets = new Level2Assets(this);
		level2Assets.loadAllAssets();
		
		// Map
		tiledMap = assetManager.get("data/levels/Level2/level2.tmx", TiledMap.class);
		
		// Map Renderer
		orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		
		// Game Flow
		levelActive = false;
		level2GameFlow = new Level2GameFlow(this, playState);
		
		// Music
		levelThemeSong = assetManager.get("data/audio/music/desertTheme.mp3", Music.class);
	}
	
	
	@Override
	public void update(float dt) {
		// Check if level should start
		if (!levelActive) { return; }
		if (gameFlowStart) {
			level2GameFlow.initialize(Gdx.graphics.getDeltaTime());
			gameFlowStart = false;
		}
		
		
		// Update Game Objects
		updateGameObjects(dt);
		
		// Camera
		camera.position.y += 0.3f;
		camera.update();
		
		// Game Flow
		level2GameFlow.update(dt);
		
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		orthogonalTiledMapRenderer.setView(camera);
		orthogonalTiledMapRenderer.render();
	}
	
	// Helper Methods
	private void startArrays() {
		enemyBulletList = playState.getEnemyBulletList();
		allEnemies = playState.getAllEnemies();
		energyTankList = playState.getAllItems();
		explosionList = playState.getExplosionList();
		explosionList.clear();
		allEnemies.clear();
		enemyBulletList.clear();
		energyTankList.clear();
	}
	
	private void updateGameObjects(float dt) {
		// All Enemies
		for (int i = 0; i < allEnemies.size; i++) {
			if (allEnemies.get(i).isActive()) {
				allEnemies.get(i).update(dt);
			}
			if (allEnemies.get(i).isDead()) {
				allEnemies.removeIndex(i);
			}
		}
		
		// Enemy Bullets
		for (int i = 0; i < enemyBulletList.size; i++) {
			enemyBulletList.get(i).update(dt);
			if (enemyBulletList.get(i).isDead()) {
				enemyBulletList.removeIndex(i);
			}
		}
		
		// Energy Tank
		for (int i = 0; i < energyTankList.size; i++) {
			energyTankList.get(i).update(dt);
			if (energyTankList.get(i).isDead()) {
				energyTankList.removeIndex(i);
			}
		}
		
		// Explosions
		for (int i = 0; i < explosionList.size; i++) {
			explosionList.get(i).update(dt);
			if (explosionList.get(i).getExplosionStatus()) {
				explosionList.removeIndex(i);
			}
		}
	}
	
	public void dispose() {
		// Dispose Renderer
		orthogonalTiledMapRenderer.dispose();
	}
	
	// Getters
	public Array<GameObjects> getAllEnemies(){
		return allEnemies;
	}
	
}
