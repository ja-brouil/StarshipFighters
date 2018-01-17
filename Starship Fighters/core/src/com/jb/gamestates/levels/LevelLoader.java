package com.jb.gamestates.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

public class LevelLoader {
	
	// Tiled Map
	private TiledMap tiledMap;
	private String level;
	private Rectangle[] spawnPoints;

	public LevelLoader(String level) {
		this.level = level;
		tiledMap = new TiledMap();
	}
	
	public void init() {
		tiledMap.getLayers().get("Basic Alien Spawn Point");
	}
	
	public void update(float dt) {
		
	}
	
	public void render() {
		
	}

	public void dispose() {
		
	}
}
