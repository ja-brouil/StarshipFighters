package com.jb.level.level1;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.jb.gameobjects.enemies.SamusShipBoss;
import com.jb.gameobjects.items.EnergyTank;
import com.jb.gamestates.PlayState;
import com.jb.images.Background;
import com.jb.level.Level;
import com.jb.main.Game;

public class Level1 extends Level{

	// Spawn Points
	private Array<Vector2> spawnPoints;
	private Vector2 bossSpawnPoint;

	// Basic Aliens
	private Array<BasicAlien> basicAlienList;
	private Array<EnemyBullets> enemyBulletList;
	
	// Boss
	private SamusShipBoss samusShipBoss;

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

	// Background
	private Background bg1;
	private Background bg2;

	// Collision Handling
	private Level1CollisionDetection level1CollisionDetection;
	
	// Asset Loader
	private Level1Assets level1Assets;

	public Level1(String mapName, PlayState playState, AssetManager assetManager) {
		super(mapName, playState, assetManager);
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
		
		// Boss Object
		MapGroupLayer bossSpawnGroupLayer = (MapGroupLayer) tiledMap.getLayers().get("Boss");
		MapLayer bossSpawnLayer = bossSpawnGroupLayer.getLayers().get("BossSpawn");
		bossSpawnPoint = new Vector2(bossSpawnLayer.getObjects().get("Boss").getProperties().get("x", Float.class), bossSpawnLayer.getObjects().get("Boss").getProperties().get("y", Float.class));
		samusShipBoss = new SamusShipBoss(-100, -100, 0, 0, this, playState, assetManager);
		
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
		if (basicAlienList.size == 0 && !spawnEnabled && spawnNumber <= 5 && !spawnboss) {
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
					spawnEnabled = false;
					spawnTimerElapsedTime = TimeUtils.millis();
					spawnTimerCooldown = 8000;
					
				}
			}
		}
		
		// Spawn Boss
		if (spawnboss && basicAlienList.size == 0) {
			
			// Stop Level Music
			levelThemeSong.stop();
			
			if (TimeUtils.timeSinceMillis(spawnTimerElapsedTime) > spawnTimerCooldown) {
				
				// Start boss
				startBoss();
				
				// Start Boss Theme
				Music bossTheme = assetManager.get("data/audio/music/bossbattle.mp3", Music.class);
				bossTheme.setLooping(true);
				bossTheme.play();
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
		
		// Draw Boss
		samusShipBoss.draw(spriteBatch);

		// Draw Explosions
		for (Explosion explosion : explosionList) {
			explosion.draw(spriteBatch);
		}	
	}

	// Update all Game Objects
	private void updateGameObjects(float dt) {
		// Update Enemies
		for (int i = 0; i < basicAlienList.size; i++) {
			basicAlienList.get(i).update(dt);
			if (basicAlienList.get(i).isDead()) {
				explosionList.add(new Explosion(basicAlienList.get(i).getX(), basicAlienList.get(i).getY(), 0, 0, playState, assetManager));
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
		
		// Update Boss
		samusShipBoss.update(dt);
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
	
	// Start Boss
	private void startBoss() {
		
		// Move Boss from OffScreen
		samusShipBoss.setVelX(-2f);
		samusShipBoss.setVelY(-1f);
		samusShipBoss.setX(bossSpawnPoint.x);
		samusShipBoss.setY(bossSpawnPoint.y);
		samusShipBoss.setActive(true);
		spawnboss = false;
		
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
