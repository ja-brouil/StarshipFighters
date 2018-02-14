package com.jb.gameobjects.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Item {

	// Location
	protected float x, y;
	
	// Velocity
	protected float dx, dy;
	
	// Gameplay
	protected boolean isDead;
	protected Rectangle hitBox;
	
	// Asset Manager
	protected AssetManager assetManager;

	public Item(float x, float y, float dx, float dy, AssetManager assetManager) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.assetManager = assetManager;
	}
	
	// Methods
	public abstract void itemEffect();
	public abstract void update(float dt);
	public abstract void draw(SpriteBatch spriteBatch);
	
	// Getters
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getDx() {
		return dx;
	}

	public float getDy() {
		return dy;
	}

	public boolean isDead() {
		return isDead;
	}
	
	public void setDeathStatus(boolean isDead) {
		this.isDead = isDead;
	}
	
	public Rectangle getBoundingBox() {
		return hitBox;
	}

}
