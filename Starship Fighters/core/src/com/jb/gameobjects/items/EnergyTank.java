package com.jb.gameobjects.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.jb.animation.Animator;
import com.jb.gameobjects.GameObjects;

public class EnergyTank extends GameObjects{

	// Graphics
	private int frameCols;
	private int frameRows;
	private float frameLengthTime;
	private float animationTimer;
	private String energyTankPathName = "data/ammo/lifetank2.png";
	private Animator energyTankAnimatior;
	
	// Collision Box
	private float width;
	private float height;

	// Gameplay
	private int healthRegenValue;

	public EnergyTank(float x, float y, float dx, float dy, AssetManager assetManager) {
		super(x, y, dx, dy, assetManager);
		
		isDead = false;
		
		init();
	}
	
	// Initialization
	private void init() {
		// Variables for Animation
		frameCols = 4;
		frameRows = 1;
		energyTankPathName = "data/ammo/lifetank2.png";
		frameLengthTime = 1f / 5f;

		// Start Animation
		energyTankAnimatior = new Animator(frameCols, frameRows, energyTankPathName, frameCols, frameRows, frameLengthTime, assetManager);
		
		// Bounding Box for collision
		width = 56f / 4f;
		height = 18f;
		collisionBounds = new Rectangle(x, y, width, height);
		
		// Health Regen Value
		healthRegenValue = 50;
	}

	// Update Items
	public void update(float dt) {
		// Update location
		x += dx;
		y += dy;
		
		// Update Collision Bounds
		collisionBounds.setPosition(x, y);
		setLimits();

	}

	// Draw
	public void draw(SpriteBatch spriteBatch) {
		animationTimer += Gdx.graphics.getDeltaTime();
		spriteBatch.draw(energyTankAnimatior.getAnimationFrames().getKeyFrame(animationTimer, true), x, y, 56f / 4f, 18f);
	}


	// Limits
	private void setLimits() {

		// Set Speed Limits
		if (dx >= 4) {
			dx = 4;
		}

		if (dx <= -4) {
			dx = -4;
		}

		if (dy >= 4) {
			dy = 4;
		}

		if (dy <= -4) {
			dy = -4;
		}

		// Remove if reached bottom of the screen
		if (y < -assetManager.get(energyTankPathName, Texture.class).getHeight()) {
			isDead = true;
		}

	}
	
	public int getHealthRegenValue() {
		return healthRegenValue;
	}
	
	public boolean getRemovalStatus() {
		return isDead;
	}

}
