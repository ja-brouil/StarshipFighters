package com.jb.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jb.assetmanagers.ImageManager;
import com.jb.assetmanagers.audio.MusicManager;
import com.jb.assetmanagers.audio.SoundManager;
import com.jb.gamestates.GameStateManager;
import com.jb.input.GameInputProcessor;

public class Game extends ApplicationAdapter {
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 800;
	
	private SpriteBatch spriteBatch;
	private OrthographicCamera cam;
	private Viewport viewport;
	
	private GameStateManager gsm;
	private GameInputProcessor input;
	
	private static ImageManager imageManager;
	
	private MusicManager musicManager;
	private SoundManager soundManager;
	
	private float dt;
	
	
	@Override
	public void create () {
		// When game is started
		
		// Graphics 
		spriteBatch = new SpriteBatch();
		
		// Audio
		musicManager = new MusicManager();
		soundManager = new SoundManager();
		
		// Setting Cameras to the size of the game window
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		viewport = new ExtendViewport(WIDTH, HEIGHT, cam);
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update(); // commit cam changes from translate

		
		// Start Keyboard Input
		input = new GameInputProcessor();
		Gdx.input.setInputProcessor(input);
		
		// Start Image Manager
		imageManager = new ImageManager();
		
		// Start Game State Manager
		gsm = new GameStateManager(this);
	
	}
	
	// Main Game Loop
	@Override
	public void render() {
		// Get time passed, render if only enough time has passed
		
		// Debug purposes
		dt += Gdx.graphics.getDeltaTime();
		
		
		if (dt >= 1) {
			System.out.println("Memory Used: " + (Gdx.app.getJavaHeap() / 100000) + "mb");
			System.out.println("Native Memory Used: " + (Gdx.app.getNativeHeap() / 100000) + "mb");
			System.out.println("FPS: " + Gdx.graphics.getFramesPerSecond());
			dt = 0;
		}
		
		
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();
		
	}
	
	@Override
	public void dispose() {
		spriteBatch.dispose();
		musicManager.disposeAllMusic();
		soundManager.disposeAllSound();
	}
	
	// Update Viewport to adjust the screen size
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
	
	// Getters + Setters	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	public MusicManager getMusicManager() {
		return musicManager;
	}
	
	public SoundManager getSoundManager() {
		return soundManager;
	}
	
	public OrthographicCamera getCamera() {
		return cam;
	}
	
	public GameInputProcessor getInput() {
		return input;
	}
	
	public static ImageManager getImageManager() {
		return imageManager;
	}
}
