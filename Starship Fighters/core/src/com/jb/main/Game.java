package com.jb.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jb.gamestates.GameStateManager;
import com.jb.input.GameInputProcessor;

public class Game extends ApplicationAdapter {
	
	// Main Window Height
	public static final int WIDTH = 640;
	public static final int HEIGHT = 800;
	
	// Graphics renderer and camera 
	private SpriteBatch spriteBatch;
	private OrthographicCamera cam;
	private Viewport viewport;
	
	// GameStates and Input handling
	private GameStateManager gsm;
	private GameInputProcessor input;
	
	// Asset Manager
	private AssetManager assetManager;
	
	// DEBUG
	private float dt;
	
	
	@Override
	public void create () {
		
		// Graphics 
		spriteBatch = new SpriteBatch();
		
		// Start Asset Manage
		assetManager = new AssetManager();
		
		
		// Setting Cameras to the size of the game window
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		viewport = new ExtendViewport(WIDTH, HEIGHT, cam);
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update(); // commit cam changes from translate

		
		// Start Keyboard Input
		input = new GameInputProcessor();
		Gdx.input.setInputProcessor(input);
		
		// Start Game State Manager
		gsm = new GameStateManager(this);
	
	}
	
	// Main Game Loop
	@Override
	public void render() {
		// DEBUG | FPS + Memory Usage
		dt += Gdx.graphics.getDeltaTime();
		if (dt >= 5) {
			System.out.println("Memory Used: " + (Gdx.app.getJavaHeap() / 100000) + "mb");
			System.out.println("Native Memory Used: " + (Gdx.app.getNativeHeap() / 100000) + "mb");
			System.out.println("FPS: " + Gdx.graphics.getFramesPerSecond());
			dt = 0;
		}
		
		// Update then Render | Update only only if enough time has passed
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();
		
	}
	
	// Dispose
	@Override
	public void dispose() {
		spriteBatch.dispose();
		assetManager.dispose();
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
	
	public GameInputProcessor getInput() {
		return input;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}
}
