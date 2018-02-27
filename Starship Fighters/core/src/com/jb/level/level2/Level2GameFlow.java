package com.jb.level.level2;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.utils.Array;
import com.jb.gameobjects.enemies.EnemyTypes;
import com.jb.gameobjects.player.Player;
import com.jb.gamestates.PlayState;
import com.jb.level.SpawnPoints;

public class Level2GameFlow {
	
	// Level 2 Access
	private Level2 level2;
	private PlayState playState;
	
	// Map Layer
	private MapLayer playerSpawnLayer;
	private MapLayer enemySpawnLayer;
	
	// Spawn Points
	private Array<SpawnPoints> enemySpawnPoints;
	
	// Player
	private Player player;
	private boolean releaseControl = false;
	
	public Level2GameFlow(Level2 level2, PlayState playState) {
		this.level2 = level2;
		this.playState = playState;
		player = playState.getPlayer();
	}
	
	public void initialize(float dt) {
		// Set Player below screen to have him come up
		// Get Maplayer
		playerSpawnLayer = level2.getTiledMap().getLayers().get("Player");
		player.setX(playerSpawnLayer.getObjects().get("Start").getProperties().get("x", Float.class));
		player.setY(playerSpawnLayer.getObjects().get("Start").getProperties().get("y", Float.class));
		player.setDY(3f);
		
		// Move Health Bar 
		playState.getHealthBar().setDY(0.3f);
		
		// Get Enemy Spawn Layers
		enemySpawnPoints = new Array<SpawnPoints>();
		enemySpawnLayer = level2.getTiledMap().getLayers().get("Enemy");
		for (MapObject mapObject : enemySpawnLayer.getObjects()) {
			if (mapObject.getName().equalsIgnoreCase("BA")) {
				enemySpawnPoints.add(new SpawnPoints(mapObject.getProperties().get("x", Float.class), mapObject.getProperties().get("y", Float.class), EnemyTypes.BasicAlien, level2));
			} else if (mapObject.getName().equalsIgnoreCase("KA")) {
				enemySpawnPoints.add(new SpawnPoints(mapObject.getProperties().get("x", Float.class), mapObject.getProperties().get("y", Float.class), EnemyTypes.KamikazeAlien, level2));
			} else {
				enemySpawnPoints.add(new SpawnPoints(mapObject.getProperties().get("x", Float.class), mapObject.getProperties().get("y", Float.class)));
			}
		}
		
		// Spawn all enemies
		for (SpawnPoints allEnemySpawnPoint : enemySpawnPoints) {
			allEnemySpawnPoint.spawnEnemy();
		}
		
	}
	
	public void update(float dt) {
		// If player reaches the stop, give back control to the player
		if (player.getY() >= playerSpawnLayer.getObjects().get("Stop").getProperties().get("y", Float.class) && !releaseControl) {
			player.setAllowedOutOfBounds(false);
			player.getPlayerInput().setAllowedInput(true);
			playState.setInputAllowed(true);
			player.setDY(0);
			
			// Start Music
			level2.getLevelMusic().setLooping(true);
			level2.getLevelMusic().play();
			
			// Release Control change
			releaseControl = true;
		}
		
	}
	
	// Getter
	public Array<SpawnPoints> getEnemySpawnPoints(){
		return enemySpawnPoints;
	}
}
