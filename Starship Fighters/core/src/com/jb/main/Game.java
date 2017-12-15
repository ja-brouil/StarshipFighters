package com.jb.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jb.assetmanagers.ImageManager;
import com.jb.handler.GameStateManager;
import com.jb.input.GameInputProcessor;

public class Game extends ApplicationAdapter {
	
	public static final int WIDTH = 640;
	public static final int HEIGHT = 800;
	
	private SpriteBatch spriteBatch;
	private OrthographicCamera cam;
	private OrthographicCamera HUDcam;
	private Viewport viewport;
	
	private GameStateManager gsm;
	private GameInputProcessor input;
	
	private static ImageManager imageManager;
	
	
	@Override
	public void create () {
		// When game is started
		spriteBatch = new SpriteBatch();
		
		// Graphics 
		
		
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

	@Override
	public void render() {
		// Get time passed, render if only enough time has passed
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();
		
	}
	
	@Override
	public void dispose() {
		spriteBatch.dispose();
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
	
	public OrthographicCamera getCamera() {
		return cam;
	}
	
	public OrthographicCamera getHUDCam() {
		return HUDcam;
	}
	
	public GameInputProcessor getInput() {
		return input;
	}
	
	public static ImageManager getImageManager() {
		return imageManager;
	}
}
