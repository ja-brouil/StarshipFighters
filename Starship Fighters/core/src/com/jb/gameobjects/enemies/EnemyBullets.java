package com.jb.gameobjects.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jb.gameobjects.GameObjects;

public class EnemyBullets extends GameObjects{
	
	// Physics
	private float minSpeed;
	private float dy;
	
	// Graphics
	private String pathName;
	private Texture allTexture;
	private TextureRegion[] enemyBulletTexture;
	
	// Game Mechanics
	private int damageValue;
	
	// Removal and Collision
	private boolean isOffScreen;

	public EnemyBullets(float x, float y, float dx, float dy) {
		super(x, y, dx, dy);
		
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		
		// Pathname for graphics
		pathName = "data/ammo/enemyBullet.png";
		
		// Initial Boolean Status
		isOffScreen = false;
		minSpeed = -50;
		
		// Initialize 
		init();
		
	}
	
	// Initialize
	private void init() {
		
		// GamePlay
		damageValue = 5;
		
		// Graphics || Might rewrite this later if it lags too much. Create a static method for this? Or have a permanent object
		// with all the variables already.
		
		allTexture = new Texture(Gdx.files.internal(pathName));
		TextureRegion[][] tmp = TextureRegion.split(allTexture, allTexture.getWidth() / 2, allTexture.getHeight() / 1);
		enemyBulletTexture = new TextureRegion[2];
		enemyBulletTexture[0] = tmp[0][0];
		enemyBulletTexture[1] = tmp[0][1];
		
		// HitBox
		collisionBounds = new Rectangle(x, y, 4f, 9f);
	}
	
	// Update
	public void update(float dt) {
		// Bullets are going down only
		y -= dy;
		
		// Update HitBox
		collisionBounds.set(x, y, 4f, 9f);
		
		// Prevent Bullet max speed
		setLimits();
	}
	
	// Render
	public void draw(SpriteBatch spriteBatch) {
		
		spriteBatch.draw(enemyBulletTexture[0].getTexture(), x, y, 4, 11, 0, 0, 4, 11, false, true );
		
	}
	
	// Limits
	private void setLimits() {
		
		if (dy < minSpeed) {
			dy = minSpeed;
		}
		
		if (y < 0 - 11) {
			removeBullets();
		}
		
	}
	
	// Remove Bullets from out of screen
	public void removeBullets() {
		isOffScreen = true;
	}
	
	public boolean getRemovalStatus() {
		return isOffScreen;
	}

}
