package com.jb.level;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.jb.gamestates.PlayState;

public abstract class Level {
	
	// Map Info
	protected TiledMap tiledMap;
	protected MapLayer spawnLayer;
	protected MapGroupLayer mapGroupLayer;

	// Map Location
	protected String mapName;
	
	// PlayState
	protected PlayState playState;
	
	// Level Theme Song
	protected Music levelThemeSong;
	
	// Asset Manager
	protected AssetManager assetManager;
	
	public Level(String mapName, PlayState playState, AssetManager assetManager) {
		this.mapName = mapName;
		this.playState = playState;
		this.assetManager = assetManager;
	}

	// Methods
	public abstract void update(float dt);
	public abstract void render(SpriteBatch spriteBatch);
	
}
