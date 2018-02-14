package com.jb.level.level1;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.jb.gameobjects.GameObjects;
import com.jb.gameobjects.enemies.Explosion;
import com.jb.gameobjects.enemies.level1enemies.EnemyBullets;
import com.jb.gameobjects.enemies.level1enemies.SamusShipBoss;
import com.jb.gameobjects.items.Item;
import com.jb.gamestates.PlayState;
import com.jb.images.Background;
import com.jb.level.Level;
import com.jb.main.Game;

public class Level1 extends Level {

	// Map
	private String mapName = "data/levels/level1.tmx";
	
	// Level 1 Game Flow
	private Level1GameFlow level1GameFlow;

	// Basic Aliens
	private Array<EnemyBullets> enemyBulletList;
	private Array<GameObjects> allEnemies;

	// Boss
	private SamusShipBoss samusShipBoss;

	// Explosion Array
	private Array<Explosion> explosionList;

	// Item List
	private Array<Item> energyTankList;

	// Background
	private Background bg1;
	private Background bg2;

	// Collision Handling
	private Level1CollisionDetection level1CollisionDetection;

	// Asset Loader
	private Level1Assets level1Assets;

	public Level1(PlayState playState, AssetManager assetManager) {
		super(playState, assetManager);
		init();
	}

	// Load Level
	public void init() {
		
		// Start Arraylists
		startArrayLists();

		// Load TMX Map
		tiledMap = new TmxMapLoader().load(mapName);
		
		// Load Assets
		level1Assets = new Level1Assets(this);
		level1Assets.loadLevel1Assets();
		assetManager.finishLoading();
	
		// Start Game Flow
		level1GameFlow = new Level1GameFlow(this, tiledMap);
		level1GameFlow.init();
		
		// Boss Ship
		samusShipBoss = level1GameFlow.getSamusBossShip();

		// Start Collision Handler
		level1CollisionDetection = new Level1CollisionDetection(playState, this);

		// Background
		loadBackgrounds();

		// Load Initial Enemies
		level1GameFlow.loadInitialEnemies();
	}

	// Load Backgrounds
	private void loadBackgrounds() {
		bg1 = new Background(0, 0, 0, -0.25f, 640, 805, true, "data/background/level1background.jpg", assetManager);
		bg2 = new Background(0, -800, 0, -0.25f, 640, 805, true, "data/background/level1background.jpg", assetManager);
	}

	// Level Mechanics
	public void update(float dt) {

		// Game Objects
		updateGameObjects(dt);

		// Collision Handling
		level1CollisionDetection.checkCollisions();
		
		// Update Level
		level1GameFlow.updateLevel();

	}

	// Draw Level Elements
	public void render(SpriteBatch spriteBatch) {

		// Draw Background
		bg2.drawInverted(spriteBatch, false, true);
		bg1.draw(spriteBatch);

		// Draw Enemies
		for (GameObjects enemy: allEnemies) {
			enemy.draw(spriteBatch);
		}

		// Draw Enemy Bullets
		for (EnemyBullets enemyBullets : enemyBulletList) {
			enemyBullets.draw(spriteBatch);
		}

		// Draw Boss
		samusShipBoss.draw(spriteBatch);
		
		// Draw Items
		for (Item energyTank : energyTankList) {
			energyTank.draw(spriteBatch);
		}

		// Draw Explosions
		for (Explosion explosion : explosionList) {
			explosion.draw(spriteBatch);
		}
	}
	
	// HELPER METHODS
	// Update all Game Objects
	private void updateGameObjects(float dt) {

		// Update Background
		bg1.update(dt);
		bg1.checkLimits(1, Game.HEIGHT, -1, -800, 0, 0);
		bg2.update(dt);
		bg2.checkLimits(1, Game.HEIGHT, -1, -800, 0, 0);

		// Update all Enemies
		for (int i = 0; i < allEnemies.size; i++) {
			allEnemies.get(i).update(dt);
			if (allEnemies.get(i).isDead()) {
				allEnemies.removeIndex(i);
				i--;
			}
		}

		// Enemy Bullets
		for (int i = 0; i < enemyBulletList.size; i++) {
			enemyBulletList.get(i).update(dt);
			// Remove Bullets
			if (enemyBulletList.get(i).getRemovalStatus()) {
				enemyBulletList.removeIndex(i);
				i--;
			}
		}

		// Update Items
		for (int i = 0; i < energyTankList.size; i++) {
			energyTankList.get(i).update(dt);
			if (energyTankList.get(i).isDead()) {
				energyTankList.removeIndex(i);
				i--;
			}
		}

		// Update Explosion
		for (int i = 0; i < explosionList.size; i++) {
			explosionList.get(i).update(dt);
			// Remove Explosion
			if (explosionList.get(i).getExplosionStatus()) {
				explosionList.removeIndex(i);
				i--;
			}
		}

		// Update Boss
		samusShipBoss.update(dt);
	}


	// Start Array lists
	private void startArrayLists() {
		energyTankList = playState.getAllItems();
		allEnemies = playState.getAllEnemies();
		enemyBulletList = playState.getEnemyBulletList();
		explosionList = playState.getExplosionList();
	}

	// Getters
	public AssetManager getAssetManager() {
		return assetManager;
	}

	public Array<EnemyBullets> getEnemyBulletList() {
		return enemyBulletList;
	}

	public Array<Explosion> getExplosionList() {
		return explosionList;
	}
	
	public Array<GameObjects> getAllGameObjects(){
		return allEnemies;
	}

	public SamusShipBoss getSamueShipBoss() {
		return samusShipBoss;
	}
}
