package com.jb.assetmanagers.music;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {
	
	private static HashMap<String, Music> musicManager;

	// Start Music
	static {
		musicManager = new HashMap<>();
	}
	
	// Add Music
	public static void addMusic(String pathName, String name) {
		Music music = Gdx.audio.newMusic(Gdx.files.internal(pathName));
		musicManager.put(name, music);
	}
	
	// Remove Music
	public static void removeMusic(String name) {
		musicManager.remove(name);
	}
	
	// Play Music | This only plays it once! Use loop if you want it to cycle through
	public static void playMusic(String name) {
		musicManager.get(name).play();
	}
	
	// Loop Music
	public static void loopMusic(String name) {
		musicManager.get(name).setLooping(true);
		musicManager.get(name).play();
	}
	
	// Stop Music
	public static void stopMusic(String name) {
		musicManager.get(name).setLooping(false);
		musicManager.get(name).stop();
	}
	
	// Dispose of music asset
	public static void disposeMusic(String name) {
		musicManager.get(name).dispose();
	}
	
	// Dispose Everything
	public static void disposeAllMusic() {
		for (HashMap.Entry<String, Music> music : musicManager.entrySet()) {
			music.getValue().dispose();
		}
	}

}
