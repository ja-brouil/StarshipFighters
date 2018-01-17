package com.jb.assetmanagers.audio;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class MusicManager {
	
	private HashMap<String, Music> musicManager;
	private Music currentSong;
	
	public MusicManager() {
		// Start Music Manager
		musicManager = new HashMap<String, Music>();
	}
	
	
	// Add Music
	public  void addMusic(String pathName, String name) {
		Music music = Gdx.audio.newMusic(Gdx.files.internal(pathName));
		musicManager.put(name, music);
	}
	
	// Remove Music
	public  void removeMusic(String name) {
		musicManager.remove(name);
	}
	
	// Play Music | This only plays it once! Use loop if you want it to cycle through
	public  void playMusic(String name) {
		musicManager.get(name).play();
		currentSong = musicManager.get(name);
	}
	
	// Loop Music
	public  void loopMusic(String name) {
		musicManager.get(name).setLooping(true);
		musicManager.get(name).play();
		currentSong = musicManager.get(name);
	}
	
	// Stop Music
	public  void stopMusic(String name) {
		musicManager.get(name).setLooping(false);
		musicManager.get(name).stop();
		musicManager.get(name).dispose();
	}
	
	// Get the current song playing
	public  Music getMusicPlaying() {
		return currentSong;
	}
	
	// Dispose of music asset
	public  void disposeMusic(String name) {
		musicManager.get(name).dispose();
	}
	
	// Dispose Everything
	public  void disposeAllMusic() {
		for (HashMap.Entry<String, Music> music : musicManager.entrySet()) {
			music.getValue().dispose();
		}
	}
	
	// Get Music Manager HashSet
	public HashMap<String, Music> getMusicHashSet(){
		return musicManager;
	}

}
