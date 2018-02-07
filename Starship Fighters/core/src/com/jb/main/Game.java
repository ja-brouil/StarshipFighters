package com.jb.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jb.gamestates.GameStateManager;
import com.jb.gamestates.Transition;
import com.jb.input.GameInputProcessor;

public class Game extends ApplicationAdapter {

	// Main Window Height
	public static final int WIDTH = 640;
	public static final int HEIGHT = 800;
	public static String title = "Starship Fighters";

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
	public void create() {

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
		Gdx.graphics.setTitle(title + " | Memory usage: " + (Gdx.app.getJavaHeap() / 100000) + "mb" + " | FPS: "
				+ Gdx.graphics.getFramesPerSecond());
		
		// Clear Screen
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
