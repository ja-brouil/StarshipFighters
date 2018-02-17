package com.jb.level;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jb.gamestates.PlayState;

public abstract class Level {
	
	// Map Info
	protected TiledMap tiledMap;
	protected MapLayer spawnLayer;
	protected MapGroupLayer mapGroupLayer;

	// Map Location
	protected String mapName;
	
	// Map Renderer
	protected OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
	
	// PlayState
	protected PlayState playState;
	
	// Level Theme Song
	protected Music levelThemeSong;
	
	// Asset Manager
	protected AssetManager assetManager;
	
	public Level(PlayState playState, AssetManager assetManager) {
		this.playState = playState;
		this.assetManager = assetManager;
	}
	
	public Level(PlayState playState, AssetManager assetManager, OrthogonalTiledMapRenderer orthogonalTiledMapRenderer) {
		this.playState = playState;
		this.assetManager = assetManager;
		this.orthogonalTiledMapRenderer = orthogonalTiledMapRenderer;
	}

	// Methods
	public abstract void update(float dt);
	public abstract void render(SpriteBatch spriteBatch);
	
	// Getters | Setters
	public Music getLevelMusic() {
		return levelThemeSong;
	}
	
	public void setLevelMusic(Music levelThemeSong) {
		this.levelThemeSong = levelThemeSong;
	}
	
	public MapLayer getSpawnLayer() {
		return spawnLayer;
	}
	
	public TiledMap getTiledMap() {
		return tiledMap;
	}
	
	public PlayState getPlayState() {
		return playState;
	}
	
	public AssetManager getAssetManager() {
		return assetManager;
	}
}
