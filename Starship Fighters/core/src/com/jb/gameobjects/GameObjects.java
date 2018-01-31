package com.jb.gameobjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObjects {
	
	protected float x,y;
	protected float dx, dy;
	protected boolean left, right, up, down, shoot, missile;
	protected int healthbar;
	protected Rectangle collisionBounds;
	
	
	// Blank constructor for JSON | Dispose
	public GameObjects() {
		
	}
	
	public GameObjects(float x, float y, float dx, float dy) {
		// No need to extra stuff in the constructor here
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
	public abstract void update(float dt, boolean xWrap, boolean yWrap);
	public abstract void draw(SpriteBatch spriteBatch);
	
	// Use this to change HP
	public void setHP(int hp, boolean replaceHP) {
		if (replaceHP) {
			healthbar = hp;
		} else {
			healthbar += hp;
		}
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
}
