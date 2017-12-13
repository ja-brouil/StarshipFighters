package com.jb.gameobjects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jb.gameobjects.GameObjects;
import com.jb.gameobjects.enemies.BasicAlien;
import com.jb.main.Game;

/*
 * Extra Info:
 * Bullet Size for Sprite: 8 width, 16 height
 */


public class PlayerBullets extends GameObjects{
	
	// Physics
	private float maxSpeed;
	private float dy;
	
	// Graphics
	private String pathname;
	private Texture allTexture;
	private TextureRegion[] bulletTexture;
	
	// Game Mechanics
	private boolean doubleBullets, tripleBullets;
	
	// Removal and Collision
	private boolean isOffScreen;

	public PlayerBullets(float x, float y, float dx, float dy) {
		super(x, y, dx, dy);
		
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		
		// Pathname for Graphics
		pathname = "data/ammo/bulletfinal.png";
		
		// Initial Boolean Status 
		isOffScreen = false;
		maxSpeed = 50;
		
		// Initialize
		init();
	}
	
	// Initialize Bullets
	public void init() {
		
		// Bullet Upgrades
		doubleBullets = false;
		tripleBullets = false;
		
		// Graphics || Might rewrite this later if it lags too much. Create a static method for this? Or have a permanent object
		// with all the variables already.
		allTexture = new Texture(Gdx.files.internal(pathname));
		TextureRegion[][] tmp = TextureRegion.split(allTexture, allTexture.getWidth() / 2, allTexture.getHeight() / 1);
		bulletTexture = new TextureRegion[1];
		bulletTexture[0] = tmp[0][0];
		
		// Rectangle
		collisionBounds = new Rectangle();
		collisionBounds.set(x, y, 4f, 9f);
		
	}
	
	// Update Bullet Positions
	public void update(float dt) {

		// Bullets going only up
		y += dy;
		
		// Update collisionBounds
		collisionBounds.set(x, y, 4f, 9f);
		
		
		// Prevent bullet speed
		setLimits();
		
	}
	
	// Draw Bullets
	public void draw(SpriteBatch spriteBatch){
		
		if (doubleBullets) {
			spriteBatch.draw(bulletTexture[0], x, y);
			spriteBatch.draw(bulletTexture[0], x + 5, y);
		} else if (tripleBullets) {
			spriteBatch.draw(bulletTexture[0], x, y);
			spriteBatch.draw(bulletTexture[0], x + 5, y);
			spriteBatch.draw(bulletTexture[0], x + 10, y);
		} else {
			spriteBatch.draw(bulletTexture[0], x, y);
			
			//spriteBatch.draw(bulletTexture[0], x - 32, y - 32);
			//spriteBatch.draw(bulletTexture[0], x + 32, y - 32);
		}
		
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
	
	public Rectangle getcollisionBounds() {
		return collisionBounds;
	}
	
	public boolean getRemovalStatus() {
		return isOffScreen;
	}
	
	
}
