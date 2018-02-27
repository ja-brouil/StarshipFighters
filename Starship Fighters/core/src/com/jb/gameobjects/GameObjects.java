package com.jb.gameobjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObjects {
	
	// Screen Position
	protected float x,y;
	
	// Velocity
	protected float dx, dy;
	
	// Input Booleans
	protected boolean left, right, up, down, shoot, missile;
	
	// Health
	protected int healthbar;
	
	// Damage Value
	protected int damageValue;
	
	// Dead status
	protected boolean isDead;
	protected boolean removeFromArray;
	
	// HitBox
	protected Rectangle collisionBounds;
	
	// Asset Manager
	protected AssetManager assetManager;
	
	// Activate Object
	protected boolean isActive;
	
	// Constructor
	public GameObjects(float x, float y, float dx, float dy, AssetManager assetManager) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.assetManager = assetManager;
		isActive = false;
	}
	
	// Cast result to int if needed
	public static float clamp(float max, float min, float value) {
		if (value > max) {
			value = max;
		}
		
		if (value < min) {
			value = min;
		}
		
		return value;
	}
	
	// All Objects Require Update + Render
	public abstract void update(float dt);
	public abstract void draw(SpriteBatch spriteBatch);
	
	// HP
	public int getHealthBar() {
		return healthbar;
	}
	
	public void setHealthBar(int healthBar) {
		this.healthbar = healthBar;
	}
	
	public void changeHealthBar(int damageValue) {
		healthbar += damageValue;
	}
	
	// Setters | Getters
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getDX() {
		return dx;
	}

	public void setVelX(float dx) {
		this.dx = dx;
	}

	public float getDY() {
		return dy;
	}

	public void setVelY(float dy) {
		this.dy = dy;
	}
	
	public Rectangle getBoundingBox() {
		return collisionBounds;
	}
	
	public int getHP() {
		return healthbar;
	}

	public boolean isDead() {
		return isDead;
	}
	
	public void setIsDead(boolean isDead) {
		this.isDead = isDead;
	}
	
	public boolean isRemoved() {
		return removeFromArray;
	}

	public int getDamageValue() {
		return damageValue;
	}
	
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public boolean isActive() {
		return isActive;
	}

}
