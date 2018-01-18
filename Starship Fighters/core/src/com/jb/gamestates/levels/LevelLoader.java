package com.jb.gamestates.levels;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class LevelLoader {
	
	// Tiled Map
	private TiledMap tiledMap;
	private String level;
	private String mapName;
	private Array<Vector2> spawnPoints;
	private MapLayer spawnLayer;

	public LevelLoader(String level, String mapName) {
		this.level = level;
		this.mapName = mapName;
		init();
	}
	
	public void init() {
		spawnPoints = new Array<Vector2>();
		tiledMap = new TmxMapLoader().load(mapName);
		spawnLayer = tiledMap.getLayers().get("BasicAlienSpawn");
		loadSpawnLocations(spawnLayer);
		
		for (int i = 0; i < spawnPoints.size; i++) {
			System.out.println(spawnPoints.get(i) + " x: " + spawnPoints.get(i).x);
			System.out.println(spawnPoints.get(i) + " y: " + spawnPoints.get(i).y);
		}
	}
	
	private void loadSpawnLocations(MapLayer layer) {
		for (MapObject mapObject : layer.getObjects()) {
			spawnPoints.add(new Vector2(mapObject.getProperties().get("x", Float.class), mapObject.getProperties().get("y", Float.class)));
		}
	}
	
	public void update(float dt) {
		
	}
	
	public void render() {
		
	}

	public void dispose() {
		
	}
	
	public String getLevel() {
		return level;
	}
}
