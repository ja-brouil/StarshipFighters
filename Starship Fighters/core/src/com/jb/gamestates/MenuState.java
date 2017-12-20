package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.jb.assetmanagers.audio.MusicManager;
import com.jb.assetmanagers.audio.SoundManager;
import com.jb.gameobjects.player.Player;
import com.jb.images.Background;
import com.jb.input.GameKeys;
import com.jb.main.Game;

public class MenuState extends GameState {

	private String[] menuChoices;
	private String title = "Starship Fighters";
	private BitmapFont bitmapFont;
	private int currentOption;
	private Background menuBackground;
	private String menuBackgroundPath;
	private String menuMusicName = "Menu Music";
	private String MenuMusicPathName = "data/audio/music/menumusic.mp3";
	private String choiceOptionName;
	private String choiceSoundNamePathName;
	private Player actorPlayer;
	private boolean[] stopXMovement;

	public MenuState(GameStateManager gsm) {
		super(gsm);

		// Strings
		choiceOptionName = "Move Cursor";
		choiceSoundNamePathName = "data/audio/sound/Menu Select.wav";

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
		menuBackground = new Background(Game.WIDTH, 0, -0.75f, 0, 1920, 1080, true, menuBackgroundPath);

		// Start Actor Player
		actorPlayer = new Player(Game.WIDTH + (Game.WIDTH / 2) - 32, Game.HEIGHT / 2, -0.75f, 0, null);

		// Boolean checks
		stopXMovement = new boolean[3];
		for (int i = 0; i < stopXMovement.length; i++) {
			stopXMovement[i] = true;
		}

		// Start Music
		MusicManager.addMusic(MenuMusicPathName, menuMusicName);
		MusicManager.loopMusic(menuMusicName);

		// Start Sound
		SoundManager.addSound(choiceSoundNamePathName, choiceOptionName);
	}

	@Override
	public void handleInput() {

		// Up/Down
		if (GameKeys.isPressed(GameKeys.UP)) {
			if (currentOption > 0) {
				currentOption--;
				SoundManager.playSound(choiceOptionName, 1.0f);
			}
		}

		if (GameKeys.isPressed(GameKeys.DOWN)) {
			if (currentOption < menuChoices.length - 1) {
				currentOption++;
				SoundManager.playSound(choiceOptionName, 1.0f);
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

			// Remove Sound Effect
			SoundManager.removeSound(choiceOptionName);

			// Delete actor player
			actorPlayer = null;

		} else if (currentOption == 1) {

			Gdx.app.exit();
		}
	}

	@Override
	public void update(float dt) {

		// Handle Input
		handleInput();

		// Update Background
		if (menuBackground.getX() < 0) {
			menuBackground.setMovingBackgroundEnabled(false);
			menuBackground.setDx(0);
		}
		menuBackground.update(dt);

		if (actorPlayer != null) {
			// Update Player Background
			if (menuBackground.getDx() != 0) {
				actorPlayer.updateActor(dt, false);
			}

			// Move Ship up
			if (menuBackground.getDx() == 0) {
				actorPlayer.setDX(0);
				actorPlayer.setDY(5.25f);
				actorPlayer.updateActor(dt, true);

			}

			// Remove Actor if past screen
			if (actorPlayer.getY() > Game.HEIGHT) {
				actorPlayer = null;
			}
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

		// Draw Background
		menuBackground.draw(spriteBatch);

		// Draw Title
		bitmapFont.setColor(Color.WHITE);
		bitmapFont.draw(spriteBatch, title, Game.WIDTH / 2 - 50, Game.HEIGHT - 100);

		// Draw Menu
		for (int i = 0; i < menuChoices.length; i++) {
			if (currentOption == i) {
				bitmapFont.setColor(Color.YELLOW);
			} else {
				bitmapFont.setColor(Color.WHITE);
			}

			bitmapFont.draw(spriteBatch, menuChoices[i], Game.WIDTH / 2 - 5, 200 - (20 * i));

		}

		// Draw Player Actor
		if (actorPlayer != null) {
			actorPlayer.draw(spriteBatch);
		}

		// End SpriteBatch drawing
		spriteBatch.end();

	}

	// Free up System Resources when no longer needed
	@Override
	public void dispose() {
		MusicManager.disposeAllMusic();
		SoundManager.disposeAllSound();
		bitmapFont.dispose();
	}

}
