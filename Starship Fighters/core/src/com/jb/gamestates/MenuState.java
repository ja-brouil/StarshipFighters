package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.jb.animation.Animator;
import com.jb.assetmanagers.audio.MusicManager;
import com.jb.assetmanagers.audio.SoundManager;
import com.jb.images.Background;
import com.jb.input.GameKeys;
import com.jb.main.Game;

public class MenuState extends GameState {

	// Menu Options + Pause time
	private String[] menuChoices;
	private String title = "Starship Fighters";
	private BitmapFont bitmapFont;
	private int currentOption;
	private float pauseTime;
	
	// Background
	private Background menuBackground;
	private Background menuBackground2;
	private String menuBackgroundPath;
	
	// Opening Animation Sequence
	private Animator openingSequence;
	private float animationTime;
	
	// Music for Menu
	private boolean loadMusic = true;
	private MusicManager musicManager;
	private String menuMusicName = "Menu Music";
	private String MenuMusicPathName = "data/audio/music/menumusic.mp3";
	
	// Sound for Choices
	private SoundManager soundManager;
	private String choiceOptionName;
	private String choiceSoundNamePathName;
	


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
		menuBackground2 = new Background(0, 0, -0.75f, 0, 1920, 1080, true, menuBackgroundPath);
		
		// Opening Animation Sequence
		openingSequence = new Animator(8, 10, "data/transitions/IntroAnimation.png", 8, 10, 1/15f);

		// Start Music
		musicManager = game.getMusicManager();
		if (loadMusic) {
			musicManager.addMusic(MenuMusicPathName, menuMusicName);
			loadMusic = false;
		}
		musicManager.loopMusic(menuMusicName);

		// Start Sound
		soundManager = gsm.getGame().getSoundManager();
		soundManager.addSound(choiceSoundNamePathName, choiceOptionName);
	}

	@Override
	public void handleInput() {
		
		// Pause when exiting out of playstate
		pauseTime += Gdx.graphics.getDeltaTime();
		if (pauseTime < 0.3f) {
			return;
		}

		// Up | Down
		if (GameKeys.isPressed(GameKeys.UP)) {
			if (currentOption > 0) {
				currentOption--;
				soundManager.playSound(choiceOptionName, 1.0f);
			}
		}

		if (GameKeys.isPressed(GameKeys.DOWN)) {
			if (currentOption < menuChoices.length - 1) {
				currentOption++;
				soundManager.playSound(choiceOptionName, 1.0f);
			}
		}

		// Enter
		if (GameKeys.isPressed(GameKeys.ENTER)) {
			select();
		}

		// Escape
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			dispose();
			Gdx.app.exit();
		}
		
		GameKeys.update();
	}

	// Select Option
	private void select() {
		// play
		if (currentOption == 0) {
			gsm.setState(GameStateManager.PLAY);

			// Stop Music + Remove music from HashMap for memory
			musicManager.stopMusic(menuMusicName);

			// Remove Sound Effect
			soundManager.removeSound(choiceOptionName);


		} else if (currentOption == 1) {
			dispose();
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
		
		if (menuBackground2.getX() < -1 * Game.WIDTH) {
			menuBackground2.setMovingBackgroundEnabled(false);
			menuBackground2.setDx(0);
		}
		
		menuBackground.update(dt);
		menuBackground2.update(dt);
		
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
		menuBackground2.draw(spriteBatch);
		
		// Draw the Opening Animation
		if (menuBackground.getDx() == 0 && menuBackground2.getDx() == 0) {
			if (openingSequence != null) {
				animationTime += Gdx.graphics.getDeltaTime();
				spriteBatch.draw(openingSequence.getAnimationFrames().getKeyFrame(animationTime), 150, 400);

				if (openingSequence.getAnimationFrames().isAnimationFinished(animationTime)) {
					openingSequence.dispose();
					openingSequence = null;
				}
			}
		}

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

		// End SpriteBatch drawing
		spriteBatch.end();

	}

	// Free up System Resources when no longer needed
	@Override
	public void dispose() {
		menuBackground.dispose();
		menuBackground2.dispose();
		soundManager.disposeAllSound();
		musicManager.disposeAllMusic();
	}

}
