package com.jb.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jb.handler.GameStateManager;

public class Game extends ApplicationAdapter {
	
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	
	private SpriteBatch spriteBatch;
	private OrthographicCamera cam;
	private OrthographicCamera HUDcam;
	
	// Step Time
	private static final float STEP = 1 / 60f;
	private float accum;
	
	private GameStateManager gsm;
	
	
	@Override
	public void create () {
		// When game is started
		spriteBatch = new SpriteBatch();
		// Setting Cameras to the size of the game window
		cam = new OrthographicCamera();
		cam.setToOrtho(false, WIDTH, HEIGHT);
		HUDcam = new OrthographicCamera();
		HUDcam.setToOrtho(false, WIDTH, HEIGHT);
		gsm = new GameStateManager(this);
		
	}

	@Override
	public void render() {
		// Main Loop in LibGDX
		// Get time passed, render if only enough time has passed
		accum += Gdx.graphics.getDeltaTime();
		while (accum >= STEP) {
			accum -= STEP;
			gsm.update(STEP);
			gsm.render();
		}
		
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
}
