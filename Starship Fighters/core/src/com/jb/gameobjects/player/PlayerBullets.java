package com.jb.gameobjects.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jb.assetmanagers.ImageManager;
import com.jb.gameobjects.GameObjects;
import com.jb.main.Game;

public class PlayerBullets extends GameObjects{
	
	// Physics
	private float maxSpeed;
	
	// Graphics
	private String pathname;
	private ImageManager bulletImageManager;
	private TextureRegion bulletTexture;
	
	// Removal and Collision
	private boolean isOffScreen;

	public PlayerBullets(float x, float y, float dx, float dy) {
		super(x, y, dx, dy);
		
		// Pathname for Graphics
		pathname = "data/ammo/bullet.gif";
		
		// Initial Boolean Status 
		isOffScreen = false;
	}
	
	// Initialize Bullets
	public void init() {
		bulletImageManager = new ImageManager();
		bulletImageManager.loadTexture(pathname, "Bullet");
		
	}
	
	// Update Bullet Positions
	public void update(float dt) {
		
		// Bullets going only up
		y += dy;
		
		// Prevent bullet speed
		setLimits();
		
	}
	
	// Draw Bullets
	public void draw(SpriteBatch spriteBatch){
		spriteBatch.draw(bulletTexture, x, y);
	}
	
	// Limits
	private void setLimits() {
		if (dy > maxSpeed) {
			dy = maxSpeed;
		}
		
		if (y > Game.HEIGHT) {
			removeBullets();
		}
		
	}
	
	// Remove bullets from out of screen
	public void removeBullets() {
		isOffScreen = true;
	}
	
	public boolean getRemovalStatus() {
		return isOffScreen;
	}
}
