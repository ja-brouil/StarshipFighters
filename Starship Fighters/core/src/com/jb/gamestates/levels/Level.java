package com.jb.gamestates.levels;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jb.gamestates.PlayState;

public class Level {
	
	// Tiled Map
	private TiledMap tiledMap;
	private MapLayer spawnLayer;
	
	// Level Number
	private static int levelNumber;
	
	// Map Location
	private String mapName;
	
	// Spawn Points
	private Array<Vector2> spawnPoints;
	
	// PlayState
	private PlayState playState;

	public Level(String mapName, PlayState playState) {
		this.playState = playState;
		this.mapName = mapName;
		init();
	}
	
	// Load Level 
	public void init() {
		
		// Load TMX Map
		spawnPoints = new Array<Vector2>();
		tiledMap = new TmxMapLoader().load(mapName);
		spawnLayer = tiledMap.getLayers().get("BasicAlienSpawn");
		loadSpawnLocations(spawnLayer);
	}
	
	// Load Spawn Locations
	private void loadSpawnLocations(MapLayer layer) {
		for (MapObject mapObject : layer.getObjects()) {
			spawnPoints.add(new Vector2(mapObject.getProperties().get("x", Float.class), mapObject.getProperties().get("y", Float.class)));
		}
	}
	
	// Load Backgrounds
	private void loadBackgrounds() {
		
	}
	
	public void update(float dt) {
		
	}
	
	public void render() {
		
	}
	
	// Dispose of All Assets
	public void dispose() {
		
	}
	
	public static int getLevel() {
		return levelNumber;
	}
	
	public Array<Vector2> getEnemyArray(){
		return spawnPoints;
	}
}
