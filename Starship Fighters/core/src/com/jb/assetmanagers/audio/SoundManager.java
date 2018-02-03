package com.jb.assetmanagers.audio;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;


public class SoundManager {

	// Objects Needed
	private HashMap<String, Sound> soundManager;
	private Sound currentSound;
	private AssetManager assetManager;

	public SoundManager(AssetManager assetManager) {
		this.assetManager = assetManager;
		soundManager = new HashMap<String, Sound>();
	}

	// Add Sound
	public void addSound(String pathName, String name) {
		assetManager.load(pathName, Sound.class);
		Sound sound = assetManager.get(pathName, Sound.class);
		soundManager.put(name, sound);
	}

	// Remove Sound
	public  void removeSound(String name) {
		soundManager.remove(name);
	}

	// Play sound | Use Sound if you need this to play constantly
	// through
	public void playSound(String name, float volume) {
		soundManager.get(name).play(volume);
		currentSound = soundManager.get(name);
	}

	// Stop Sound
	public  void stopSound(String name) {
		soundManager.get(name).stop();
	}
	
	// Get Sound Loaded
	public  Sound getSound(String name) {
		return currentSound;
	}

	// Dispose of Sound asset
	public void disposeSound(String name) {
		soundManager.get(name).dispose();
	}

	// Dispose Everything
	public  void disposeAllSound() {
		for (HashMap.Entry<String, Sound> sounds : soundManager.entrySet()) {
			sounds.getValue().dispose();
		}
	}
	
	// Get all sounds
	public  HashMap<String, Sound> getAllSounds(){
		return soundManager;
	}

}
