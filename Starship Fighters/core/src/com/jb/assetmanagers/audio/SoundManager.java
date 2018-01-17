package com.jb.assetmanagers.audio;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

	private HashMap<String, Sound> soundManager;
	private Sound currentSound;

	public SoundManager() {
		
		soundManager = new HashMap<String, Sound>();
	}

	// Add Sound
	public void addSound(String pathName, String name) {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal(pathName));
		soundManager.put(name, sound);
	}

	// Remove Sound
	public  void removeSound(String name) {
		soundManager.remove(name);
	}

	// Play sound | Use Sound if you need this to play constantly
	// through
	public  void playSound(String name, float volume) {
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
