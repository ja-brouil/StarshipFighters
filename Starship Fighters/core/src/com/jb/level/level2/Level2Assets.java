package com.jb.level.level2;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Level2Assets {
	
	// Asset Manager
	private AssetManager assetManager;
	
	// Assets to Load
	// Map
	private String mapFilePath = "data/levels/Level2/level2.tmx"; 
	
	// Music
	private String level2ThemeSongPath = "data/audio/music/desertTheme.mp3";
	
	// SFX
	
	// Enemies
	
	// Items
	
	// Misc
	
	public Level2Assets(Level2 level2) {
		assetManager = level2.getAssetManager();
	}
	
	// Initialize
	public void loadAllAssets() {
		assetManager.load(mapFilePath, TiledMap.class);
		assetManager.load(level2ThemeSongPath, Music.class);
		assetManager.finishLoading();
		
	}
}
