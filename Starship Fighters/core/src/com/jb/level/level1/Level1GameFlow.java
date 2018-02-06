package com.jb.level.level1;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jb.gameobjects.enemies.BasicAlien;
import com.jb.gameobjects.enemies.KamikazeAlien;
import com.jb.gameobjects.enemies.SamusShipBoss;
import com.jb.level.Level;

public class Level1GameFlow {

	// Level 1 Access
	private Level1 level1;

	// Spawn Points
	private Array<Vector2> spawnPoints;
	private Array<Vector2> kamikazeSpawnPoints;
	private Vector2 bossSpawnPoint;

	// Spawn Counter
	private int spawnNumber;
	private long spawnTimerCooldown;
	private long spawnTimerElapsedTime;
	private boolean isDelayOn;
	private boolean spawnBoss;

	// Map and Layer
	private TiledMap tiledMap;
	private MapLayer spawnLayer;

	// ArrayList Access
	private Array<BasicAlien> basicAlienList;
	private Array<KamikazeAlien> kamikazeAlienList;

	// Map Layers
	private MapGroupLayer mapGroupLayer;

	// Boss Object
	private SamusShipBoss samusShipBoss;

	// Asset Manager
	private AssetManager assetManager;

	public Level1GameFlow(Level1 level1, TiledMap tiledMap) {
		this.level1 = level1;
		this.tiledMap = tiledMap;
		assetManager = level1.getAssetManager();
		basicAlienList = level1.getBasicAlienList();
		kamikazeAlienList = level1.getKamikazeAlienList();
	}

	// Init
	public void init() {

		// Spawn Cooldown
		spawnTimerCooldown = 2000;

		// Spawn Enabler
		isDelayOn = false;
		spawnBoss = false;

		// Start Spawn Points
		spawnPoints = new Array<Vector2>();
		kamikazeSpawnPoints = new Array<Vector2>();

		// Get Basic Enemies Spawn points
		spawnNumber = 0;
		mapGroupLayer = (MapGroupLayer) tiledMap.getLayers().get("EnemySpawn");
		spawnLayer = mapGroupLayer.getLayers().get(spawnNumber);

		// Boss Object
		MapGroupLayer bossSpawnGroupLayer = (MapGroupLayer) tiledMap.getLayers().get("Boss");
		MapLayer bossSpawnLayer = bossSpawnGroupLayer.getLayers().get("BossSpawn");
		bossSpawnPoint = new Vector2(bossSpawnLayer.getObjects().get("Boss").getProperties().get("x", Float.class),
				bossSpawnLayer.getObjects().get("Boss").getProperties().get("y", Float.class));
		samusShipBoss = new SamusShipBoss(-100, -100, 0, 0, level1, level1.getPlayState(), assetManager);
	}

	// Check for next wave
	public void updateLevel() {
		// Check if all enemies are dead
		boolean areAllEnemiesDead = basicAlienList.size == 0 && kamikazeAlienList.size == 0;
		if (areAllEnemiesDead) {
			// Start Delay
			if (!isDelayOn) {
				spawnTimerElapsedTime = TimeUtils.millis();
				isDelayOn = true;
			}

			// Check if Delay is over
			if (TimeUtils.timeSinceMillis(spawnTimerElapsedTime) > spawnTimerCooldown) {
				startNextWave();
			}
		}
	}


	// Start next wave
	private void startNextWave() {
		// Get Spawn Points
		spawnLayer = mapGroupLayer.getLayers().get(spawnNumber);
		getSpawnPoints(spawnLayer);

		// Spawn Basic Alien
		for (Vector2 alienSpawnPoint : spawnPoints) {
			basicAlienList.add(
					new BasicAlien(alienSpawnPoint.x, alienSpawnPoint.y, 0, -5, 1000L, -15, -20, level1, assetManager));
		}
		
		// Spawn Kamikaze Alien
		for (Vector2 kamikazeAlien: kamikazeSpawnPoints) {
			kamikazeAlienList.add(new KamikazeAlien(kamikazeAlien.x, kamikazeAlien.y, 0, -8, -25, level1, assetManager));
		}

		// Increase Spawn Counter
		spawnNumber++;

		// Allow Delay again
		isDelayOn = false;
	}

	// Clear Array of Wave Points and get new Spawn Points
	private void getSpawnPoints(MapLayer layer) {
		// Clear Array
		spawnPoints.clear();
		kamikazeSpawnPoints.clear();
		
		// Get spawn Locations
		for (MapObject mapObject : layer.getObjects()) {
			if (mapObject.getName() != null && mapObject.getName().equalsIgnoreCase("Ka")) {
				kamikazeSpawnPoints.add(new Vector2(mapObject.getProperties().get("x", Float.class), mapObject.getProperties().get("y", Float.class)));
			} else {
				spawnPoints.add(new Vector2(mapObject.getProperties().get("x", Float.class),
						mapObject.getProperties().get("y", Float.class)));
			}
		}
	}

	// Initial Enemies
	public void loadInitialEnemies() {
	// Get spawn Points
		getSpawnPoints(spawnLayer);

		// Start Initial Enemies
		for (int i = 0; i < spawnPoints.size; i++) {
			basicAlienList.add(new BasicAlien(spawnPoints.get(i).x, spawnPoints.get(i).y, 0, -5, 1000L, -15, -20,
					level1, assetManager));
		}
	}

	// Boss Stuff

	// Start Boss
	private void startBoss() {
		// Move Boss from OffScreen
		samusShipBoss.setVelX(-2f);
		samusShipBoss.setVelY(-1f);
		samusShipBoss.setX(bossSpawnPoint.x);
		samusShipBoss.setY(bossSpawnPoint.y);
		samusShipBoss.setActive(true);
	}

	// Play Music
	public void startMusic() {
		level1.setLevelMusic(assetManager.get("data/audio/music/level1.mp3", Music.class));
		level1.getLevelMusic().setLooping(true);
		level1.getLevelMusic().play();
	}
	
	// Getters
	public SamusShipBoss getSamusBossShip() {
		return samusShipBoss;
	}

}
