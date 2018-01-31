package com.jb.gamestates.levels;

import java.sql.Time;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.jb.assetmanagers.audio.MusicManager;
import com.jb.gameobjects.enemies.BasicAlien;
import com.jb.gameobjects.enemies.SamusShipBoss;
import com.jb.gamestates.PlayState;
import com.jb.images.Background;
import com.jb.main.Game;

public class Level1 {
	
	// Tiled Map
	private TiledMap tiledMap;
	private MapLayer spawnLayer;
	
	// Level Number
	private static int levelNumber;
	
	// Map Location
	private String mapName;
	
	// Spawn Points
	private Array<Vector2> spawnPoints;
	private Vector2 bossSpawnPoint;
	
	// Spawn Counter
	private int spawnNumber;
	private long spawnTimerCooldown;
	private long spawnTimerElapsedTime;
	private boolean spawnEnabled;
	
	// Boss Spawn Variables
	private boolean spawnboss;
	
	// Music
	private MusicManager musicManager;
	private String level1MusicPathName = "data/audio/music/level1.mp3";
	private String level1Music = "Level 1 Music";
	private String victoryMusicPathName = "data/audio/music/victorytheme.mp3";
	private String victoryMusicName = "Victory";
	
	// Background
	private Background bg1;
	private Background bg2;
	
	// PlayState
	private PlayState playState;

	public Level1(String mapName, PlayState playState) {
		this.playState = playState;
		this.mapName = mapName;
		init();
	}
	
	// Load Level 
	public void init() {
		
		// Load TMX Map
		tiledMap = new TmxMapLoader().load(mapName);
		
		// Get Basic Enemies Spawn points
		spawnPoints = new Array<Vector2>();
		spawnLayer = tiledMap.getLayers().get("BasicAlienSpawn");
		loadSpawnLocations(spawnLayer);
		
		// Boss Spawn | Not sure how to just get the boss spawn object only, read into this 
		MapObject bossSpawn = tiledMap.getLayers().get("BossSpawn").getObjects().get("Boss");
		bossSpawnPoint = new Vector2(bossSpawn.getProperties().get("x", Float.class), bossSpawn.getProperties().get("y", Float.class));

		// Background 
		loadBackgrounds();
		
		// Music
		loadMusic();
	}
	
	// Load Spawn Locations
	private void loadSpawnLocations(MapLayer layer) {
		
		// Get Spawn Points
		for (MapObject mapObject : layer.getObjects()) {
			spawnPoints.add(new Vector2(mapObject.getProperties().get("x", Float.class), mapObject.getProperties().get("y", Float.class)));
		}
		
		// Start Initial Enemies
		for (int i = 0; i < spawnPoints.size; i++) {
			playState.getBasicAliens().add(new BasicAlien(spawnPoints.get(i).x, spawnPoints.get(i).y, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
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
		bg1 = new Background(0, 0, 0, -0.25f, 640, 805, true, "data/background/level1background.jpg");
		bg2 = new Background(0, -800, 0, -0.25f, 640, 805, true, "data/background/level1background.jpg");
	}
	
	// Load Music
	private void loadMusic() {
		musicManager = playState.getGSM().getGame().getMusicManager();
		musicManager.addMusic(level1MusicPathName, level1Music);
		musicManager.loopMusic(level1Music);
		musicManager.addMusic(victoryMusicPathName, victoryMusicName);
	}
	
	// Level Mechanics
	public void update(float dt) {
		
		// Update Background
		bg1.update(dt);
		bg1.checkLimits(1, Game.HEIGHT, -1, -800, 0, 0);
		bg2.update(dt);
		bg2.checkLimits(1, Game.HEIGHT, -1, -800, 0, 0);
		
		// Check if all enemies are dead
		if (playState.getBasicAliens().size == 0 && !spawnEnabled && spawnNumber <= 5) {
			spawnEnabled = true;
			spawnTimerElapsedTime = TimeUtils.millis();
		}
		
		// Spawn New Enemies | 1 second pause in between
		if (spawnEnabled) {
			if (TimeUtils.timeSinceMillis(spawnTimerElapsedTime) > spawnTimerCooldown && spawnNumber < 5) {
				for (int i = 0; i < spawnPoints.size; i++) {
					playState.getBasicAliens().add(new BasicAlien(spawnPoints.get(i).x, spawnPoints.get(i).y, 0, -5, 1000L, -15, playState.getEnemyBulletList(), -20, playState));
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
		
		// Boss Check
		if (spawnNumber == 5 && spawnboss) {
			playState.setNewBoss(new SamusShipBoss(100, 975, 2, -2, playState.getEnemyBulletList(), playState.getExplosionList(), playState.getBasicAliens(), playState));
			spawnboss = false;
		}
	}
	
	// Render Extra Level Graphics | If needed
	public void render(SpriteBatch spriteBatch) {
		bg2.drawInverted(spriteBatch, false, true);
		bg1.draw(spriteBatch);
	}
	
	// Dispose of All Assets
	public void dispose() {
		musicManager.disposeMusic(level1Music);
		musicManager.disposeMusic(victoryMusicName);
		bg1.dispose();
		bg2.dispose();
	}
	
	public static int getLevel() {
		return levelNumber;
	}
	
	public Array<Vector2> getEnemyArray(){
		return spawnPoints;
	}
}
