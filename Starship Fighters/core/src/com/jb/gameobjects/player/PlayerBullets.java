package com.jb.gameobjects.player;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.jb.gameobjects.GameObjects;
import com.jb.main.Game;

/*
 * Extra Info:
 * Bullet Size for Sprite: 8 width, 16 height
 */


public class PlayerBullets extends GameObjects{
	
	// Player Access
	private Player player;
	
	// Physics
	private float maxSpeed;
	private float dy;
	
	// Game Mechanics
	private boolean doubleBullets, tripleBullets;
	private int damageValue;
	
	// Removal and Collision
	private boolean isOffScreen;

	public PlayerBullets(float x, float y, float dx, float dy, AssetManager assetManager, Player player) {
		super(x, y, dx, dy, assetManager);
		
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.player = player;
		
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
		
		// GamePlay
		damageValue = -100;
		
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
			spriteBatch.draw(player.getBulletTexture(), x, y);
			spriteBatch.draw(player.getBulletTexture(), x + 5, y);
		} else if (tripleBullets) {
			spriteBatch.draw(player.getBulletTexture(), x, y);
			spriteBatch.draw(player.getBulletTexture(), x + 5, y);
			spriteBatch.draw(player.getBulletTexture(), x + 10, y);
		} else {
			spriteBatch.draw(player.getBulletTexture(), x, y);
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
	
	public int getDamageValue() {
		return damageValue;
	}
	
}
