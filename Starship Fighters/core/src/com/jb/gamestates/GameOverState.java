package com.jb.gamestates;

import java.awt.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.jb.assetmanagers.audio.MusicManager;
import com.jb.assetmanagers.audio.SoundManager;
import com.jb.gameobjects.GameObjects;
import com.jb.gameobjects.enemies.EnemyBullets;
import com.jb.gameobjects.enemies.Explosion;
import com.jb.gameobjects.player.Player;
import com.jb.gameobjects.player.PlayerBullets;
import com.jb.gamestates.levels.MasterLevel;
import com.jb.input.GameKeys;

public class GameOverState extends GameState{

	
	// Strings for Game Over
	private String gameOver = "Game Over";
	private String[] choices = {"Return to Main Menu" , "Exit"};
	private int currentOption;
	
	// SFX
	private static String selectSound = "data/audio/sound/Menu Select.wav";
	private static String choiceOptionName;
	
	// Bitmap Font
	private BitmapFont bitmapFont;
	
	// Boolean
	private boolean stopRendering = false;
	

	public GameOverState(GameStateManager gsm) {
		super(gsm);
		init();		
	}

	@Override
	public void init() {
		
		// Grab info from playstate
		
		
		// Start Bitmap Font
		bitmapFont = new BitmapFont();
		bitmapFont.getData().setScale(3);
		
		// Spritebatch start
		spriteBatch = game.getSpriteBatch();
		
		// Current Option Start
		currentOption = 0;
		
		// Add Sound effect
		SoundManager.addSound(selectSound, choiceOptionName);
		
	}
	

	@Override
	public void handleInput() {
		// Up/Down
		if (GameKeys.isPressed(GameKeys.UP)) {
			if (currentOption > 0) {
				currentOption--;
				SoundManager.playSound(choiceOptionName, 1f);
			}
		}
		
		if (GameKeys.isPressed(GameKeys.DOWN)) {
			if (currentOption < choices.length - 1) {
				currentOption++;
				SoundManager.playSound(choiceOptionName, 1.0f);
			}
		}
		
		// Enter
		if (GameKeys.isPressed(GameKeys.ENTER)) {
			select();
		}
		
		// Escape
		if (GameKeys.isPressed(GameKeys.ESCAPE)) {
			Gdx.app.exit();
		}
		
	}
	
	// Select Method
	private void select() {
		// Return to main menu
		if (currentOption == 0) {
			stopRendering = true;
			gsm.setState(GameStateManager.MENU);
		} else if(currentOption == 1) { 
			// exit
			Gdx.app.exit();
		}
	}

	@Override
	public void update(float dt) {
		
		// Handle input
		handleInput();
	
		// Don't need anything else here
		
	}

	@Override
	public void render() {
		
		// Clear Screen to black background
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
	
	}

	@Override
	public void dispose() {
		bitmapFont.dispose();
		spriteBatch.dispose();
		MusicManager.disposeAllMusic();
		SoundManager.disposeAllSound();
	}
	
	private void resetEverything() {
		
	}

}
