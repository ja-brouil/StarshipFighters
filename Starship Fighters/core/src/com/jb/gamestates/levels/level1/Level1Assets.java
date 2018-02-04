package com.jb.gamestates.levels.level1;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Level1Assets {

	/*
	 * Class loads assets for level 1
	 */

	// Asset Manager
	private AssetManager assetManager;

	// Music
	private String level1MusicPathName = "data/audio/music/level1.mp3";
	private String victoryMusicPathName = "data/audio/music/victorytheme.mp3";

	// SFX
	private String enemyBulletSoundPathname = "data/audio/sound/bombLaunching.wav";

	// Basic Alien Art
	private String basicAlienPathName = "data/spaceships/BasicEnemy.png";

	// Basic Alien Bullet Art
	private String basicAlienBulletPathName = "data/ammo/enemyBullet.png";

	// Basic Alien and Boss Explosion Art

	// Level 1 Boss Art

	public Level1Assets(Level1 level1) {
		assetManager = level1.getAssetManager();
	}
	
	// Load Level 1
	public void loadLevel1Assets() {
		loadAudio();
		loadTextures();
	}

	// Load Audio
	private void loadAudio() {
		
		// Add Music
		assetManager.load(level1MusicPathName, Music.class);
		assetManager.load(victoryMusicPathName, Music.class);

		// Add Sound
		assetManager.load(enemyBulletSoundPathname, Sound.class);
		
	}

	// Load Graphics
	private void loadTextures() {

		// Add Graphics
		assetManager.load(basicAlienPathName, Texture.class);
		assetManager.load(basicAlienBulletPathName, Texture.class);
	}

}
