package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.jb.animation.Animator;
import com.jb.gamestates.Transition.TransitionType;
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
	private Music menuMusic;
	private String MenuMusicPathName = "data/audio/music/menumusic.mp3";
	
	// Sound for Choices
	private Sound menuSound;
	private String choiceSoundNamePathName;

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
		menuBackground = new Background(Game.WIDTH, 0, -0.75f, 0, 1920, 1080, true, menuBackgroundPath, assetManager);
		menuBackground2 = new Background(0, 0, -0.75f, 0, 1920, 1080, true, menuBackgroundPath, assetManager);
		
		// Load Music + Play music
		assetManager.load(MenuMusicPathName, Music.class);
	
		
		// Load Sound
		choiceSoundNamePathName = "data/audio/sound/Menu Select.wav";
		assetManager.load(choiceSoundNamePathName, Sound.class);
		
		// Opening Animation Sequence
		openingSequence = new Animator(8, 10, "data/transitions/IntroAnimation.png", 8, 10, 1/10f, assetManager);
		
		// Update Asset Manager
		assetManager.finishLoading();
		
		// Play Music
		menuMusic = assetManager.get(MenuMusicPathName, Music.class);
		menuMusic.setLooping(true);
		menuMusic.play();
		
		// Load Sound Name
		menuSound = assetManager.get(choiceSoundNamePathName, Sound.class);

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
				menuSound.play(1.0f);
			}
		}

		if (GameKeys.isPressed(GameKeys.DOWN)) {
			if (currentOption < menuChoices.length - 1) {
				currentOption++;
				menuSound.play(1.0f);
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
			menuMusic.stop();
			gsm.setState(new Transition(gsm, this, new PlayState(gsm), TransitionType.BLACK_FADE));
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
		
		// Update Background
		menuBackground.update(dt);
		menuBackground2.update(dt);
		
	}

	@Override
	public void render() {

		// SpriteBatch
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		
		// Draw Background
		menuBackground.draw(spriteBatch);
		menuBackground2.draw(spriteBatch);
		
		// Draw the Opening Animation
		if (menuBackground.getDx() == 0 && menuBackground2.getDx() == 0) {
			animationTime += Gdx.graphics.getDeltaTime();
			spriteBatch.draw(openingSequence.getAnimationFrames().getKeyFrame(animationTime), 150, 400);
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

	// Additional dispose if needed
	@Override
	public void dispose() {
		bitmapFont.dispose();
	}

}
