package com.jb.images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/*
 *  Use this for image backgrounds | Modify this class if needed
 *  Boolean: Show if background should be playing
 *  x , y : position
 *  dx, dy : movement speed of background
 *  width/height: image location
 *  Texture: picture or animation if required
 *  Rectangle : Hitbox if needed
 */

public class Background{
	
	private float x, y, dx, dy, width, height;
	private float maxX, maxY, minX, minY;
	private boolean isBeingShown, movingBackgroundEnabled ;
	private Texture texture;
	private String pathName;
	//private Animator animator;
	
	// Static Background
	public Background(float x, float y, float width, float height, boolean isBeingShown, String pathName) {
		
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.pathName = pathName;
		this.isBeingShown = isBeingShown;
		movingBackgroundEnabled = false;
		
		init();
	}
	
	// Moving Background
	public Background(float x, float y, float dx, float dy, float width, float height, boolean isBeingShown, String pathName, float maxX, float maxY, float minX, float minY) {
		
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.width = width;
		this.height = height;
		this.pathName = pathName;
		this.isBeingShown = isBeingShown;
		this.maxX = maxX;
		this.maxY = maxY;
		this.minX = minX;
		this.minY = minY;
		
		movingBackgroundEnabled = true;
		
		init();
	}
	
	// Start
	private void init() {
		
		texture = new Texture(Gdx.files.internal(pathName));
		
	}
	
	// Limits
	private void checkLimits() {
		if (x >= maxX) {
			x = minX;
		}
		
		if (x <= minX) {
			x = maxX;
		}
		
		if (y >= maxY) {
			y = minY;
		}
		
		if (y <= minY) {
			y = maxY;
		}
	}

	
	// Update
	public void update(float dt) {
		
		if (movingBackgroundEnabled) {
			x += dx;
			y += dy;
			checkLimits();
		}
		
	}

	// Draw
	public void draw(SpriteBatch spriteBatch) {
		
		// Draw if needed to be drawn
		if (isBeingShown) {
			spriteBatch.draw(texture, x, y, width, height);
		}
		
	}

	// Setters and Getters
	
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

	public float getDx() {
		return dx;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public float getDy() {
		return dy;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getMaxX() {
		return maxX;
	}

	public void setMaxX(float maxX) {
		this.maxX = maxX;
	}

	public float getMaxY() {
		return maxY;
	}

	public void setMaxY(float maxY) {
		this.maxY = maxY;
	}

	public float getMinX() {
		return minX;
	}

	public void setMinX(float minX) {
		this.minX = minX;
	}

	public float getMinY() {
		return minY;
	}

	public void setMinY(float minY) {
		this.minY = minY;
	}

	public boolean isBeingShown() {
		return isBeingShown;
	}

	public void setBeingShown(boolean isBeingShown) {
		this.isBeingShown = isBeingShown;
	}

	public boolean isMovingBackgroundEnabled() {
		return movingBackgroundEnabled;
	}

	public void setMovingBackgroundEnabled(boolean movingBackgroundEnabled) {
		this.movingBackgroundEnabled = movingBackgroundEnabled;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
	
	
	
	
}
