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
	private boolean isBeingShown, movingBackgroundEnabled ;
	private Texture texture;
	private String pathName;
	
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
	public Background(float x, float y, float dx, float dy, float width, float height, boolean isBeingShown, String pathName) {
		
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.width = width;
		this.height = height;
		this.pathName = pathName;
		this.isBeingShown = isBeingShown;
		
		movingBackgroundEnabled = true;
		
		init();
	}
	
	// Start
	private void init() {
		
		texture = new Texture(Gdx.files.internal(pathName));
		
	}
	
	// Limits
	public void checkLimits(float xMax, float yMax, float xMin, float yMin, float xOffSet, float yOffset) {
		
		// x
		if (x + xOffSet > xMax) {
			x = xMin;
		}
		
		if (x + xOffSet < xMin) {
			x = xMax;
		}
		
		// y
		if (y + yOffset > yMax) {
			y = yMin;
		}
		
		if (y + yOffset < yMin) {
			y = yMax;
		}	
	}
	
	// Update
	public void update(float dt) {
		
		if (movingBackgroundEnabled) {
			x += dx;
			y += dy;
		}
		
	}

	// Draw
	public void draw(SpriteBatch spriteBatch) {
		
		// Draw if needed to be drawn
		if (isBeingShown) {
			spriteBatch.draw(texture, x, y, width, height);
		}
		
	}
	
	// Draw Inverted | We won't need this when have a correct background image
	public void drawInverted(SpriteBatch spriteBatch, boolean horizontal, boolean vertical) {
		// Draw if needed to be drawn
		if (isBeingShown) {
			spriteBatch.draw(texture, x, y, width, height, 0, 0, 640, 800, horizontal, vertical);
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
