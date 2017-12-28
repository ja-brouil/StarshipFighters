package com.jb.gameobjects.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.jb.animation.Animator;
import com.jb.gamestates.PlayState;

public class EnergyTank extends Item {

	// Graphics
	private int frameCols;
	private int frameRows;
	private float frameLengthTime;
	private float animationTimer;
	
	// Collision Box
	private float width;
	private float height;

	// Gameplay
	private float healthRegenValue;

	public EnergyTank(float x, float y, float dx, float dy, PlayState playState, float healthRegenValue) {
		super(x, y, dx, dy, playState);

		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.playState = playState;
		this.healthRegenValue = healthRegenValue;

		init();
	}

	private void init() {
		// Variables for Animation
		frameCols = 2;
		frameRows = 1;
		animationPathName = "data/ammo/lifetank2.png";
		frameLengthTime = 1f / 5f;

		// Start Animation
		itemAnimation = new Animator(frameCols, frameRows, animationPathName, frameCols, frameRows, frameLengthTime);
		
		// Bounding Box for collision
		width = 53f / 4f;
		height = 30f;
		collisionBounds = new Rectangle(x, y, width, height);

	}

	@Override
	// Not needed
	public void update(float dt, boolean xWrap, boolean yWrap) {}

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
		spriteBatch.draw(itemAnimation.getAnimationFrames().getKeyFrame(animationTimer, true), x, y);
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
		if (y < 0) {
			remove = true;
		}

	}
	
	public float getHealthRegenValue() {
		return healthRegenValue;
	}

}
