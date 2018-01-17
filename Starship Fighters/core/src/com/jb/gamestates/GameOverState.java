package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.jb.assetmanagers.audio.MusicManager;
import com.jb.assetmanagers.audio.SoundManager;
import com.jb.input.GameKeys;
import com.jb.main.Game;

public class GameOverState extends GameState{

	
	// Strings for Game Over
	private String gameOver = "Game Over";
	private String[] choices = {"Return to Main Menu" , "Exit"};
	private int currentOption;
	
	// SFX
	private SoundManager soundManager;
	private String selectSound = "data/audio/sound/Menu Select.wav";
	private String choiceOptionName = "Choice Sound";
	
	// Bitmap Font
	private BitmapFont bitmapFont;
	
	// Music Disposal
	private MusicManager musicManager;

	public GameOverState(GameStateManager gsm) {
		super(gsm);
		init();		
	}

	@Override
	public void init() {
		
		// Start Sound and Music
		musicManager = gsm.getGame().getMusicManager();
		soundManager = gsm.getGame().getSoundManager();
		soundManager.addSound(selectSound, choiceOptionName);
		
		// Start Bitmap Font
		bitmapFont = new BitmapFont();
		//bitmapFont.getData().setScale(3);
		
		// Spritebatch start
		spriteBatch = game.getSpriteBatch();
		
		// Current Option Start
		currentOption = 0;
		
		// Add Sound effect
		soundManager.addSound(selectSound, choiceOptionName);
		
	}
	

	@Override
	public void handleInput() {
		// Up/Down
		if (GameKeys.isPressed(GameKeys.UP)) {
			System.out.println("UP");
			if (currentOption > 0) {
				currentOption--;
				soundManager.playSound(choiceOptionName, 1f);
			}
		}
		
		if (GameKeys.isPressed(GameKeys.DOWN)) {
			System.out.println("DOWN");
			if (currentOption < choices.length - 1) {
				currentOption++;
				soundManager.playSound(choiceOptionName, 1.0f);
			}
		}
		
		// Enter
		if (GameKeys.isPressed(GameKeys.ENTER)) {
			select();
		}
		
		// Escape
		if (GameKeys.isPressed(GameKeys.ESCAPE)) {
			dispose();
			Gdx.app.exit();
		}
		
		GameKeys.update();
		
	}
	
	// Select Method
	private void select() {
		// Return to main menu
		if (currentOption == 0) {
			gsm.setState(GameStateManager.MENU);
		} else if(currentOption == 1) { 
			// exit
			dispose();
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
		spriteBatch.begin();
		
		// Draw Options
		bitmapFont.setColor(Color.WHITE);
		bitmapFont.draw(spriteBatch, gameOver, Game.WIDTH / 2 - 50, Game.HEIGHT - 100);
		
		// Draw Menu Options
		for (int i = 0; i < choices.length; i++) {
			if (currentOption == i) {
				bitmapFont.setColor(Color.YELLOW);
			} else {
				bitmapFont.setColor(Color.WHITE);
			}
			bitmapFont.draw(spriteBatch, choices[i], Game.WIDTH / 2 - 5, 200 - (20 * i));
		}
		
		// End Spritebatch
		spriteBatch.end();
	
	}

	@Override
	public void dispose() {
		bitmapFont.dispose();
		soundManager.disposeAllSound();
		musicManager.disposeAllMusic();
	}
	
}
