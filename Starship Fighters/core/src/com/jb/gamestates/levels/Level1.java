package com.jb.gamestates.levels;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
	private String level1MusicPathName = "data/audio/music/level1.mp3";
	private boolean startMusic = true;
	private String victoryMusicPathName = "data/audio/music/victorytheme.mp3";
	private Music levelThemeSong;

	// SFX
	private String enemyBulletSoundPathname = "data/audio/sound/bombLaunching.wav";

	// Basic Alien Art
	private String basicAlienPathName = "data/spaceships/BasicEnemy.png";

	// Basic Alien Bullet Art
	private String basicAlienBulletPathName = "data/ammo/enemyBullet.png";

	// Background
	private Background bg1;
	private Background bg2;

	// PlayState
	private PlayState playState;

	// Asset Manager
	private AssetManager assetManager;

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
		spawnPoints = new Array<Vector2>();
		spawnLayer = tiledMap.getLayers().get("BasicAlienSpawn");

		// Boss Spawn
		MapObject bossSpawn = tiledMap.getLayers().get("BossSpawn").getObjects().get("Boss");
		bossSpawnPoint = new Vector2(bossSpawn.getProperties().get("x", Float.class),
				bossSpawn.getProperties().get("y", Float.class));

		// Start Arraylists
		basicAlienList = new Array<BasicAlien>();
		enemyBulletList = new Array<EnemyBullets>();
		explosionList = new Array<Explosion>();
		energyTankList = new Array<EnergyTank>();

		// Asset Manager
		// Add Music
		assetManager.load(level1MusicPathName, Music.class);
		assetManager.load(victoryMusicPathName, Music.class);

		// Add Sound
		assetManager.load(enemyBulletSoundPathname, Sound.class);

		// Add Graphics
		assetManager.load(basicAlienPathName, Texture.class);
		assetManager.load(basicAlienBulletPathName, Texture.class);

		// Background
		loadBackgrounds();
		
		// Load Initial Enemies
		loadSpawnLocations(spawnLayer);
	}

	// Load Spawn Locations
	private void loadSpawnLocations(MapLayer layer) {

		// Get Spawn Points
		for (MapObject mapObject : layer.getObjects()) {
			spawnPoints.add(new Vector2(mapObject.getProperties().get("x", Float.class),
					mapObject.getProperties().get("y", Float.class)));
		}

		
		// Start Initial Enemies
		for (int i = 0; i < spawnPoints.size; i++) {
			basicAlienList.add(new BasicAlien(spawnPoints.get(i).x, spawnPoints.get(i).y, 0, -5, 1000L, -15, -20, this,
					assetManager));
		}
		

		// Start Spawn Counter
		spawnNumber = 0;

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
		assetManager.finishLoading();
	}

	// Level Mechanics
	public void update(float dt) {


		// Play the music
		if (startMusic) {
			levelThemeSong = assetManager.get(level1MusicPathName, Music.class);
			levelThemeSong.setLooping(true);
			levelThemeSong.play();
			startMusic = false;
		}

		// Update Background
		bg1.update(dt);
		bg1.checkLimits(1, Game.HEIGHT, -1, -800, 0, 0);
		bg2.update(dt);
		bg2.checkLimits(1, Game.HEIGHT, -1, -800, 0, 0);

		// Update Array Lists
		// Update Enemies
		for (int i = 0; i < basicAlienList.size; i++) {
			basicAlienList.get(i).update(dt);
			if (basicAlienList.get(i).getHP() <= 0) {
				//explosionList.add(new Explosion(basicAlienList.get(i).getX(), basicAlienList.get(i).getY(), 0, 0, null));
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

		// Collision Handling
		collisionDetection();

		// Check if all enemies are dead
		if (basicAlienList.size == 0 && !spawnEnabled && spawnNumber <= 5) {
			spawnEnabled = true;
			spawnTimerElapsedTime = TimeUtils.millis();
		}

		// Spawn New Enemies | 1 second pause in between
		if (spawnEnabled) {
			if (TimeUtils.timeSinceMillis(spawnTimerElapsedTime) > spawnTimerCooldown && spawnNumber < 5) {
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

	// Extra Dispose
	public void dispose() {

	}

	// Collision Handling Code
	private void collisionDetection() {

		// Player Bullets with Enemies
		for (int i = 0; i < playState.getPlayer().getBulletList().size; i++) {
			for (int j = 0; j < basicAlienList.size; j++) {
				if (playState.getPlayer().getBulletList().get(i).getBoundingBox()
						.contains(basicAlienList.get(j).getBoundingBox())
						|| basicAlienList.get(j).getBoundingBox()
								.contains(playState.getPlayer().getBulletList().get(i).getBoundingBox())
						|| playState.getPlayer().getBulletList().get(i).getBoundingBox()
								.overlaps(basicAlienList.get(j).getBoundingBox())
						|| basicAlienList.get(j).getBoundingBox()
								.overlaps(playState.getPlayer().getBulletList().get(i).getBoundingBox())) {
					basicAlienList.get(j).setHP(playState.getPlayer().getBulletList().get(i).getDamageValue(), false);
					playState.getPlayer().getBulletList().get(i).removeBullets();
				}
			}
		}

		// Check if Player Collides with Basic Aliens
		for (int i = 0; i < basicAlienList.size; i++) {
			if (basicAlienList.get(i).getBoundingBox().contains(playState.getPlayer().getBoundingBox())
					|| basicAlienList.get(i).getBoundingBox().overlaps(playState.getPlayer().getBoundingBox())) {
				basicAlienList.get(i).setDrop(1);
				basicAlienList.get(i).setHP(-1, true);
			
				// Add code to reduce player health here
			}
		}

		// Check if Enemy Bullets Hit the Player
		for (int i = 0; i < enemyBulletList.size; i++) {
			if (enemyBulletList.get(i).getBoundingBox().contains(playState.getPlayer().getBoundingBox())
					|| playState.getPlayer().getBoundingBox().contains(enemyBulletList.get(i).getBoundingBox())) {
				// Play Explosion on playState.getPlayer()
				//explosionList.add(new Explosion((playState.getPlayer().getX() + MathUtils.random(0, 32)),
				//		playState.getPlayer().getY() + MathUtils.random(0, 32), 0, 0, "data/hit_and_explosions/impactHit.png",
				//		"data/audio/sound/Bomb Explosion.wav", "playState.getPlayer() Hit", 3, 1, 3, 1, 1f / 40f, playState));

				// Add Code to Reduce Player Health

				// Remove Bullet
				enemyBulletList.get(i).removeBullets();
			}
		}
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
