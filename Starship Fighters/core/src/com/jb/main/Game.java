package com.jb.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.jb.handler.GameStateManager;
import com.jb.input.GameInput;

public class Game extends ApplicationAdapter {
	
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	
	private SpriteBatch spriteBatch;
	private OrthographicCamera cam;
	private OrthographicCamera HUDcam;
	
	private GameStateManager gsm;
	private GameInput input;
	
	
	@Override
	public void create () {
		// When game is started
		spriteBatch = new SpriteBatch();
		
		// Setting Cameras to the size of the game window
		cam = new OrthographicCamera();
		cam.setToOrtho(false, WIDTH, HEIGHT);
		cam.translate(new Vector2(WIDTH / 2, HEIGHT / 2)); // Move the camera from 0,0 to site of the game window
		cam.update(); // commit cam changes from translate
		HUDcam = new OrthographicCamera();
		HUDcam.setToOrtho(false, WIDTH, HEIGHT);
		HUDcam.translate(new Vector2(WIDTH / 2, HEIGHT / 2));
		HUDcam.update();
		
		// Start Game State Manager
		gsm = new GameStateManager(this);
		
		// Start Keyboard Input
		input = new GameInput();
		Gdx.input.setInputProcessor(input);
		
	}

	@Override
	public void render() {
		
		// Get time passed, render if only enough time has passed
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();
		
	}
	
	@Override
	public void dispose() {
		
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
	
	public GameInput getInput() {
		return input;
	}
}
