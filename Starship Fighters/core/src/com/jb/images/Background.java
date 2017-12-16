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
			x = 0;
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
	
	
	
}
