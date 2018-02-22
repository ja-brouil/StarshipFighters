package com.jb.level.level1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.jb.gameobjects.GameObjects;
import com.jb.gameobjects.enemies.level1enemies.BasicAlien;
import com.jb.gameobjects.enemies.level1enemies.KamikazeAlien;
import com.jb.gameobjects.enemies.level1enemies.SamusShipBoss;
import com.jb.gamestates.PlayState;

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
	private boolean noMoreEnemies;

	// Map and Layer
	private TiledMap tiledMap;
	private MapLayer spawnLayer;

	// ArrayList Access
	private Array<GameObjects> allEnemies;

	// Map Layers
	private MapGroupLayer mapGroupLayer;

	// Boss Object
	private SamusShipBoss samusShipBoss;

	// Asset Manager
	private AssetManager assetManager;
	
	// PlayState Access
	private PlayState playState;

	// Boss Music
	private float musicVolumeChange = 0.4f;
	private float volume;
	private float bossMusicTimer;
	private boolean setBossMusicOnce = true;
	private boolean startLevel1MusicOnce = true;
	private boolean startBossMusicOnce = true;
	private Music bossMusic;

	public Level1GameFlow(Level1 level1, TiledMap tiledMap) {
		this.level1 = level1;
		this.tiledMap = tiledMap;
		allEnemies = level1.getAllGameObjects();
		assetManager = level1.getAssetManager();
		playState = level1.getPlayState();
	}

	// Init
	public void init() {
		// Spawn Cooldown
		spawnTimerCooldown = 2000;

		// Spawn Enabler
		isDelayOn = false;
		spawnBoss = false;
		noMoreEnemies = false;

		// Start Spawn Points
		spawnPoints = new Array<Vector2>();
		kamikazeSpawnPoints = new Array<Vector2>();

		// Get Basic Enemies Spawn points
		spawnNumber = 9;
		mapGroupLayer = (MapGroupLayer) tiledMap.getLayers().get("EnemySpawn");
		spawnLayer = mapGroupLayer.getLayers().get(spawnNumber);

		// Boss Object
		MapGroupLayer bossSpawnGroupLayer = (MapGroupLayer) tiledMap.getLayers().get("Boss");
		MapLayer bossSpawnLayer = bossSpawnGroupLayer.getLayers().get("BossSpawn");
		bossSpawnPoint = new Vector2(bossSpawnLayer.getObjects().get("Boss").getProperties().get("x", Float.class),
				bossSpawnLayer.getObjects().get("Boss").getProperties().get("y", Float.class));
		samusShipBoss = new SamusShipBoss(-100, -100, 0, 0, level1.getPlayState(), assetManager);

		// Get Boss Music
		bossMusic = assetManager.get("data/audio/music/bossbattle.mp3", Music.class);
	}

	// Check for next wave
	public void updateLevel() {
		
		// If boss is dead, move to the next level
		if (samusShipBoss.getDeathStatus()) {
			level1.setNextLevelActive(true);
			return;
		}
		
		// Start Boss
		if (spawnBoss && allEnemies.size != 0) {
			return;
		} else if (spawnBoss) {
			startBoss();
			spawnBoss = false;
			noMoreEnemies = true;
		}
		
		// Start Boss Music
		if (noMoreEnemies) {
			startBossMusic();
		}

		// Start Level 1 Music Once
		if (startLevel1MusicOnce) {
			level1.setLevelMusic(assetManager.get("data/audio/music/level1.mp3", Music.class));
			level1.getLevelMusic().setLooping(true);
			level1.getLevelMusic().play();
			startLevel1MusicOnce = false;
		}


		// Check if all enemies are dead
		if (allEnemies.size == 0 && !spawnBoss && !noMoreEnemies) {
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
			allEnemies.add(
					new BasicAlien(alienSpawnPoint.x, alienSpawnPoint.y, 0, -5, 1000L, -15, -25, assetManager, playState));
		}

		// Spawn Kamikaze Alien
		for (Vector2 kamikazeAlien : kamikazeSpawnPoints) {
			allEnemies
					.add(new KamikazeAlien(kamikazeAlien.x, kamikazeAlien.y, 0, -8, -40, assetManager, playState));
		}

		// Increase Spawn Counter
		if (!spawnBoss) {
			spawnNumber++;
		}

		// Allow Delay again
		isDelayOn = false;

		// Check for boss
		if (spawnNumber == 10) {
			spawnBoss = true;
		}
	}

	// Clear Array of Wave Points and get new Spawn Points
	private void getSpawnPoints(MapLayer layer) {
		// Clear Array
		spawnPoints.clear();
		kamikazeSpawnPoints.clear();

		// Get spawn Locations
		for (MapObject mapObject : layer.getObjects()) {
			if (mapObject.getName() != null && mapObject.getName().equalsIgnoreCase("Ka")) {
				kamikazeSpawnPoints.add(new Vector2(mapObject.getProperties().get("x", Float.class),
						mapObject.getProperties().get("y", Float.class)));
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
			allEnemies.add(new BasicAlien(spawnPoints.get(i).x, spawnPoints.get(i).y, 0, -5, 1000L, -15, -20,
					assetManager, playState));
		}
	}

	// Boss Stuff
	// Start Boss
	private void startBoss() {
		// Move Boss from OffScreen
		samusShipBoss.setX(bossSpawnPoint.x);
		samusShipBoss.setY(bossSpawnPoint.y);
		samusShipBoss.setVelX(-2f);
		samusShipBoss.setVelY(-1f);
		samusShipBoss.setActive(true);
	}

	// Boss Music
	private void startBossMusic() {
		if (setBossMusicOnce) {
			// Reduce fade sound volume
			bossMusicTimer += Gdx.graphics.getDeltaTime();
			volume = 1 - (bossMusicTimer * musicVolumeChange);
			
			// Prevent Less than 0
			if (volume < 0) { 
				volume = 0; 
				setBossMusicOnce = false;
				level1.getLevelMusic().setLooping(false);
			}
			level1.getLevelMusic().setVolume(volume);
		}
		
		// Start Boss Music
		if (!setBossMusicOnce) {
			if (startBossMusicOnce) {
				Timer.schedule(new Task() {
					@Override
					public void run() {
						level1.setLevelMusic(bossMusic);
						level1.getLevelMusic().setLooping(true);
						level1.getLevelMusic().play();
						startBossMusicOnce = false;	
					}
				}, 4);
			}
		}
		
	}
	
	// Getters
	public SamusShipBoss getSamusBossShip() {
		return samusShipBoss;
	}

}
