package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.jb.assetmanagers.music.MusicManager;
import com.jb.handler.GameStateManager;
import com.jb.images.Background;
import com.jb.input.GameKeys;
import com.jb.main.Game;

public class MenuState extends GameState{

	private String[] menuChoices;
	private String title = "Starship Fighters";
	private BitmapFont bitmapFont;
	private int currentOption;
	private Background menuBackground;
	private String menuBackgroundPath;
	private String menuMusicName = "Menu Music";
	private String MenuMusicPathName = "data/audio/music/menumusic.mp3";

	public MenuState(GameStateManager gsm) {
		super(gsm);
		init();
		
	}

	@Override
	public void init() {
		
		// Menu Choices
		menuChoices = new String[2];
		menuChoices[0] = "Play";
		menuChoices[1] = "Exit";
	
		
		// Start Font
		// Title
		bitmapFont = new BitmapFont();
		bitmapFont.setColor(Color.WHITE);
		
		// Start Option
		currentOption = 0;	
		
		// Background
		menuBackgroundPath = "data/background/menuBackground.png";
		menuBackground = new Background(0, 0, 640, 800, true, menuBackgroundPath);
		
		// Start Music
		MusicManager.addMusic(MenuMusicPathName, menuMusicName);
		MusicManager.loopMusic(menuMusicName);
	}

	@Override
	public void handleInput() {
		
		// Up/Down
		if (GameKeys.isPressed(GameKeys.UP)) {
			if (currentOption > 0) {
				currentOption--;
			}
		}
		
		if (GameKeys.isPressed(GameKeys.DOWN)) {
			if (currentOption < menuChoices.length - 1) {
				currentOption++;
			}
		}
		
		// Enter
		if (GameKeys.isPressed(GameKeys.ENTER)) {
			select();
		}
		
		// Escape
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}
	
	// Select Option
	private void select() {
		// play
		if (currentOption == 0) {
			gsm.setState(GameStateManager.PLAY);
			// Stop Music + Remove music from hashmap for memory
			MusicManager.stopMusic(menuMusicName);
			MusicManager.removeMusic("Menu Music");
		} else if (currentOption == 1) {
			Gdx.app.exit();
		}
	}

	@Override
	public void update(float dt) {
		
		// Handle Input
		handleInput();
		
	}

	@Override
	public void render() {
		
		// Clear Screen to Black Background
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// SpriteBatch
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		
		// Draw Background
		menuBackground.draw(spriteBatch);
		
		// Draw Title
		bitmapFont.setColor(Color.WHITE);
		bitmapFont.draw(spriteBatch, title, Game.WIDTH / 2 - 50, Game.HEIGHT - 100);
		
		// Draw Menu
		for (int i = 0; i < menuChoices.length; i++) {
			if (currentOption == i ) {
				bitmapFont.setColor(Color.YELLOW);
			} else {
				bitmapFont.setColor(Color.WHITE);
			}
			
			bitmapFont.draw(spriteBatch, menuChoices[i], Game.WIDTH / 2 - 5, 200 - (20 * i));
			
		}
		
		spriteBatch.end();
		
	}
	
	// Free up System Resources when no longer needed
	@Override
	public void dispose() {
		bitmapFont.dispose();
		MusicManager.disposeMusic(menuMusicName);
	}

}
