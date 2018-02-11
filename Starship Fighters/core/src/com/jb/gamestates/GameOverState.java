package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.jb.gamestates.Transition.TransitionType;
import com.jb.input.GameKeys;
import com.jb.main.Game;

public class GameOverState extends GameState{

	
	// Strings for Game Over
	private String gameOver = "Game Over";
	private String[] choices = {"Return to Main Menu" , "Exit"};
	private int currentOption;
	
	// SFX
	private String selectSound = "data/audio/sound/Menu Select.wav";
	
	// Bitmap Font
	private BitmapFont bitmapFont;
	
	// Asset Manager
	private AssetManager assetManager;
	

	public GameOverState(GameStateManager gsm) {
		super(gsm);
		init();		
	}

	@Override
	public void init() {
		// Asset Manager
		assetManager = gsm.getGame().getAssetManager();
		
		// Start Bitmap Font
		bitmapFont = new BitmapFont();
		
		// Current Option Start
		currentOption = 0;
		
		// Add Sound effects
		assetManager.load(selectSound, Sound.class);
		assetManager.finishLoading();
		
	}
	

	@Override
	public void handleInput() {
		// Up/Down
		if (GameKeys.isPressed(GameKeys.UP)) {
			if (currentOption > 0) {
				currentOption--;
				assetManager.get(selectSound, Sound.class).play();
			}
		}
		
		if (GameKeys.isPressed(GameKeys.DOWN)) {
			if (currentOption < choices.length - 1) {
				currentOption++;
				assetManager.get(selectSound, Sound.class).play();
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
			gsm.setState(new Transition(gsm, this, new MenuState(gsm), TransitionType.BLACK_FADE));
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
	}
	
}
