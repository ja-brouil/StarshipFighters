package com.jb.gameobjects.enemies.level1enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jb.gameobjects.GameObjects;
import com.jb.gamestates.PlayState;

public class EnemyBullets extends GameObjects{
	
	// Physics
	private float minSpeed;
	private float dy;
	
	// Graphics
	private Texture allTexture;
	private TextureRegion[] enemyBulletTexture;
	
	// Game Mechanics
	private int damageValue;
	
	// Standard Constructor
	public EnemyBullets(float x, float y, float dx, float dy, int damageValue, PlayState playState, AssetManager assetManager) {
		super(x, y, dx, dy, assetManager);
		
		// Location
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.damageValue = damageValue;
		
		// Graphics
		this.assetManager = assetManager;
		
		// Initial Boolean Status
		minSpeed = -50;
		
		// Initialize 
		init();
	}
	
	// Initialize
	private void init() {
		
		// Graphics
		allTexture = assetManager.get("data/ammo/enemyBullet.png", Texture.class);
		TextureRegion[][] tmp = TextureRegion.split(allTexture, allTexture.getWidth() / 2, allTexture.getHeight() / 1);
		enemyBulletTexture = new TextureRegion[2];
		enemyBulletTexture[0] = tmp[0][0];
		enemyBulletTexture[1] = tmp[0][1];
		
		// HitBox
		collisionBounds = new Rectangle(x, y, 4f, 9f);
	}
	
	// Update
	public void update(float dt) {
		
		if (isDead) {
			return;
		}
		
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
			isDead = true;
		}
	}
	
	// Dispose
	public void dispose() {
		allTexture.dispose();
	}

	
	public boolean getRemovalStatus() {
		return isDead;
	}
	
	// Get bullet damage
	public int getDamageValue() {
		return damageValue;
	}

}
