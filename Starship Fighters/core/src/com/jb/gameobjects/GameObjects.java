package com.jb.gameobjects;

public abstract class GameObjects {
	
	protected float x,y;
	protected float dx, dy;
	protected int xBound, yBound;
	protected boolean left, right, up, down, shoot, missile;
	protected int healthbar;
	
	public GameObjects(float x, float y, float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
		this.x = x;
		this.y = y;
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

	public int getxBound() {
		return xBound;
	}

	public void setxBound(int xBound) {
		this.xBound = xBound;
	}

	public int getyBound() {
		return yBound;
	}

	public void setyBound(int yBound) {
		this.yBound = yBound;
	}
	
	

}
