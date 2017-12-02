package com.jb.gameobjects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jb.animation.Animator;
import com.jb.gameobjects.GameObjects;

public class Player extends GameObjects {
	
	// Physics
	private float maxSpeed;
	private float friction; 
	private float offSetAmount;
	private float minimumSpeed;
	
	// Graphics
	private String filePath;
	private float stateTime;
	private Animator shapeship;
	
	// Creating a box just as a test
	private float[] shapeX;
	private float[] shapeY;

	public Player(float x, float y, float dx, float dy) {
		super(x, y, dx, dy);
		
		// Graphics
		offSetAmount = 64;
		stateTime = 0f;
		
		// Limits
		maxSpeed = 10;
		friction = 2;
		minimumSpeed = -10;
		
		init();
	}
	
	public void init() {
		// File Path
		filePath = "data/spaceships/ship.png";
		stateTime = 0f;
		shapeship = new Animator(3, 8, filePath);
		
	}
	
	// Set Direction
	public void setLeft(boolean b) { left = b;}
	public void setRight(boolean b) { right = b;}
	public void setUp(boolean b) { up = b; }
	public void setDown(boolean b) { down = b; }
	
	// Update Player Status
	public void update(float dt) {
		
		
		// Left
		if (left) {
			dx -= 5 * dt;
		}
		// right
		if (right) {
			dx += 5 * dt;	
		}
		// Up
		if (up) {
			dy += 5 * dt;
		}
		// Down
		if (down) {
			dy -= 5 * dt;	 
		}
		
		// Apply Friction to the ship
		// X coordinate
		if (dx != 0 && dx > 0) {
			dx -= friction * dt;
		} else if (dx != 0 && dx < 0) {
			dx += friction * dt;
		}
		// Y coordinate
		if (dy != 0 && dy > 0) {
			dy -= friction * dt;
		} else if (dy != 0 && dy < 0) {
			dy += friction * dt;
		}
		
		// Set Maximum Speed
		if (dx > maxSpeed) { dx = maxSpeed; }
		if (dx < minimumSpeed) { dx = minimumSpeed; }
		if (dy > maxSpeed) { dy = maxSpeed; }
		if (dy < minimumSpeed) { dy = minimumSpeed; }
		
		// Update Speed
		x += dx;
		y += dy;
		
		
		// Edge of the screen move to the other side
		wrap((int) offSetAmount);
		
	}
	
	// Render Player
	public void draw(SpriteBatch spriteBatch) {
		
		stateTime += Gdx.graphics.getDeltaTime();
		
		TextureRegion currentFrame = shapeship.getAnimationFrames().getKeyFrame(stateTime, true);
		spriteBatch.draw(currentFrame, 100, 100);
		
	}
	

}
