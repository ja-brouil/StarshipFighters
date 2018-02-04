package com.jb.gamestates.levels.level1;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jb.gameobjects.enemies.BasicAlien;
import com.jb.gameobjects.enemies.EnemyBullets;
import com.jb.gameobjects.enemies.Explosion;
import com.jb.gameobjects.items.EnergyTank;
import com.jb.gamestates.PlayState;
import com.jb.images.Background;
import com.jb.main.Game;

public class Level1 {

	// Tiled Map
	private TiledMap tiledMap;
	private MapLayer spawnLayer;
	private MapGroupLayer mapGroupLayer;

	// Map Location
	private String mapName;

	// Spawn Points
	private Array<Vector2> spawnPoints;
	private Vector2 bossSpawnPoint;

	// Basic Aliens
	private Array<BasicAlien> basicAlienList;
	private Array<EnemyBullets> enemyBulletList;

	// Explosion Array
	private Array<Explosion> explosionList;

	// Item List
	private Array<EnergyTank> energyTankList;

	// Spawn Counter
	private int spawnNumber;
	private long spawnTimerCooldown;
	private long spawnTimerElapsedTime;
	private boolean spawnEnabled;

	// Boss Spawn Variables
	private boolean spawnboss;

	// Music
	private Music levelThemeSong;

	// Background
	private Background bg1;
	private Background bg2;

	// PlayState
	private PlayState playState;

	// Collision Handling
	private Level1CollisionDetection level1CollisionDetection;

	// Asset Manager
	private AssetManager assetManager;

	// Level Loader
	private Level1Assets level1Assets;

	public Level1(String mapName, PlayState playState, AssetManager assetManager) {
		this.playState = playState;
		this.mapName = mapName;
		this.assetManager = assetManager;
		init();
	}

	// Load Level
	public void init() {

		// Load TMX Map
		tiledMap = new TmxMapLoader().load(mapName);

		// Get Basic Enemies Spawn points
		spawnNumber = 0;
		spawnPoints = new Array<Vector2>();
		mapGroupLayer = (MapGroupLayer) tiledMap.getLayers().get("EnemySpawn");
		spawnLayer = mapGroupLayer.getLayers().get(spawnNumber);

		// Boss Spawn
		
		// Start Arraylists
		basicAlienList = new Array<BasicAlien>();
		enemyBulletList = new Array<EnemyBullets>();
		explosionList = new Array<Explosion>();
		energyTankList = new Array<EnergyTank>();

		// Start Collision Handler
		level1CollisionDetection = new Level1CollisionDetection(playState, this);

		// Load Assets
		level1Assets = new Level1Assets(this);
		level1Assets.loadLevel1Assets();
		assetManager.finishLoading();
		
		// Start Music
		startMusic();

		// Background
		loadBackgrounds();

		// Load Initial Enemies
		loadSpawnLocations(spawnLayer);
	}

	// Load Spawn Locations
	private void loadSpawnLocations(MapLayer layer) {

		// Get spawn Points
		getSpawnPoints(layer);

		// Start Initial Enemies
		for (int i = 0; i < spawnPoints.size; i++) {
			basicAlienList.add(new BasicAlien(spawnPoints.get(i).x, spawnPoints.get(i).y, 0, -5, 1000L, -15, -20, this,
					assetManager));
		}

		// Spawn Cooldown
		spawnTimerCooldown = 1000;
		spawnTimerElapsedTime = TimeUtils.millis();

		// Spawn Enabler
		spawnEnabled = false;
		spawnboss = false;

	}

	// Load Backgrounds
	private void loadBackgrounds() {
		bg1 = new Background(0, 0, 0, -0.25f, 640, 805, true, "data/background/level1background.jpg", assetManager);
		bg2 = new Background(0, -800, 0, -0.25f, 640, 805, true, "data/background/level1background.jpg", assetManager);
	}

	// Level Mechanics
	public void update(float dt) {

		// Update Background
		bg1.update(dt);
		bg1.checkLimits(1, Game.HEIGHT, -1, -800, 0, 0);
		bg2.update(dt);
		bg2.checkLimits(1, Game.HEIGHT, -1, -800, 0, 0);

		// Game Objects
		updateGameObjects(dt);

		// Collision Handling
		level1CollisionDetection.checkCollisions();

		// Check if all enemies are dead
		if (basicAlienList.size == 0 && !spawnEnabled && spawnNumber <= 5) {
			spawnEnabled = true;
			spawnTimerElapsedTime = TimeUtils.millis();
		}

		// Spawn New Enemies | 1 second pause in between
		if (spawnEnabled) {
			if (TimeUtils.timeSinceMillis(spawnTimerElapsedTime) > spawnTimerCooldown && spawnNumber < 5) {
				// Get next Tile Layer
				spawnLayer = mapGroupLayer.getLayers().get(spawnNumber);
				getSpawnPoints(spawnLayer);

				// Spawn new Aliens
				for (int i = 0; i < spawnPoints.size; i++) {
					basicAlienList.add(new BasicAlien(spawnPoints.get(i).x, spawnPoints.get(i).y, 0, -5, 1000L, -15,
							-20, this, assetManager));
				}

				// Stop spawn and increase spawn counter
				spawnEnabled = false;
				spawnNumber++;

				// Check if Spawn is 5 to spawn boss
				if (spawnNumber == 5) {
					spawnboss = true;
				}
			}
		}

	}

	// Draw Level Elements
	public void render(SpriteBatch spriteBatch) {

		// Draw Background
		bg2.drawInverted(spriteBatch, false, true);
		bg1.draw(spriteBatch);

		// Draw Enemies
		for (BasicAlien basicAlien : basicAlienList) {
			basicAlien.draw(spriteBatch);
		}

		// Draw Enemy Bullets
		for (EnemyBullets enemyBullets : enemyBulletList) {
			enemyBullets.draw(spriteBatch);
		}

		// Draw Explosions

	}

	// Update all Game Objects
	private void updateGameObjects(float dt) {
		// Update Enemies
		for (int i = 0; i < basicAlienList.size; i++) {
			basicAlienList.get(i).update(dt);
			if (basicAlienList.get(i).getHP() <= 0) {
				// explosionList.add(new Explosion(basicAlienList.get(i).getX(),
				// basicAlienList.get(i).getY(), 0, 0, null));
				basicAlienList.removeIndex(i);
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
			if (energyTankList.get(i).getRemovalStatus()) {
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
	}
	
	// Get Spawn Locations
	private void getSpawnPoints(MapLayer layer) {
		// Clear Array
		if (spawnPoints.size != 0) {
			spawnPoints.clear();
		}
		
		// Get spawn Locations
		for (MapObject mapObject : layer.getObjects()) {
			spawnPoints.add(new Vector2(mapObject.getProperties().get("x", Float.class),
					mapObject.getProperties().get("y", Float.class)));
		}
	}
	
	// Play Music
	private void startMusic() {
		levelThemeSong = assetManager.get("data/audio/music/level1.mp3", Music.class);
		levelThemeSong.setLooping(true);
		levelThemeSong.play();
	}

	// Getters
	public AssetManager getAssetManager() {
		return assetManager;
	}

	public Array<EnergyTank> getEnergyTankList() {
		return energyTankList;
	}

	public Array<EnemyBullets> getEnemyBulletList() {
		return enemyBulletList;
	}

	public Array<BasicAlien> getBasicAlienList() {
		return basicAlienList;
	}

	public Array<Explosion> getExplosionList() {
		return explosionList;
	}

}
