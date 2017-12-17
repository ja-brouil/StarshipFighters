package com.jb.assetmanagers.audio;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

	private static HashMap<String, Sound> soundManager;

	static {
		soundManager = new HashMap<String, Sound>();
	}

	// Add Sound
	public static void addSound(String pathName, String name) {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal(pathName));
		soundManager.put(name, sound);
	}

	// Remove Sound
	public static void removeSound(String name) {
		soundManager.remove(name);
	}

	// Play sound | Use Sound if you need this to play constantly
	// through
	public static void playSound(String name) {
		soundManager.get(name).play();
	}

	// Stop Sound
	public static void stopSound(String name) {
		soundManager.get(name).stop();
	}

	// Dispose of Sound asset
	public static void disposeSound(String name) {
		soundManager.get(name).dispose();
	}

	// Dispose Everything
	public static void disposeAllSound() {
		for (HashMap.Entry<String, Sound> sounds : soundManager.entrySet()) {
			sounds.getValue().dispose();
		}
	}

}
