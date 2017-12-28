package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.jb.assetmanagers.audio.MusicManager;
import com.jb.assetmanagers.audio.SoundManager;
import com.jb.main.Game;

public class IntroState extends GameState{

	private Texture texture;
	private String segaPathname;
	private String segaSoundName;
	private String segaLogoPathName;
	private long timer;

	public IntroState(GameStateManager gsm) {
		super(gsm);
		init();
	}

	@Override
	public void init() {
		
		// Timer
		timer = TimeUtils.millis();
		
		// Strings
		segaPathname = "data/audio/sound/Sega.wav";
		segaSoundName = "Sega Sound";
		segaLogoPathName = "data/background/segalogo.png";
		
		// Start Sound
		SoundManager.addSound(segaPathname, segaSoundName);
		
		// Texture
		texture = new Texture(Gdx.files.internal(segaLogoPathName));
		
		SoundManager.playSound(segaSoundName, 1.0f);
	}

	@Override // Not Needed
	public void handleInput() {}
	

	@Override
	public void update(float dt) {
		
		// Move Next state after Sega
		if (TimeUtils.timeSinceMillis(timer) > 3000) {
			gsm.setState(GameStateManager.MENU);
			SoundManager.removeSound(segaSoundName);
		}
		
	}

	@Override
	public void render() {
		
		// Clear Screen to Black Background
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// SpriteBatch
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		spriteBatch.draw(texture, Game.WIDTH - (texture.getWidth() / 1.85f) , Game.HEIGHT  / 2 - 50, 500, 200);
		spriteBatch.end();
		
	}
	
	// Free up System Resources when no longer needed
	// This should not be called as the game should never quit unless it crashes or the OS crashes
	@Override
	public void dispose() {
		MusicManager.disposeAllMusic();
		SoundManager.disposeAllSound();
	}
	

}
