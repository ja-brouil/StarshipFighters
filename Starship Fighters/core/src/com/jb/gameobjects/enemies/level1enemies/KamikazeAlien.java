package com.jb.gameobjects.enemies.level1enemies;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.jb.gameobjects.GameObjects;
import com.jb.gameobjects.enemies.Explosion;
import com.jb.gamestates.PlayState;

public class KamikazeAlien extends GameObjects {
	
	// PlayState
	private PlayState playState;

	// Graphics
	private TextureRegion[] sprite;
	
	// Explosion Array
	private Array<Explosion> listOfExplosion;

	// GamePlay
	private int damageValue;

	public KamikazeAlien(float x, float y, float dx, float dy, int damageValue,
			AssetManager assetManager, PlayState playState) {
		super(x, y, dx, dy, assetManager);

		// GamePlay
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.damageValue = damageValue;
		this.assetManager = assetManager;
		this.playState = playState;
		healthbar = 100;
		isDead = false;

		// Load Initial enemy data
		init();

	}

	// Load Initial Data
	private void init() {
		
		// Get Sprites
		Texture allTexture = assetManager.get("data/spaceships/BasicEnemy.png", Texture.class);
		TextureRegion[][] tmp = TextureRegion.split(allTexture, allTexture.getWidth() / 3, allTexture.getHeight() / 1);
		sprite = new TextureRegion[3];
		for (int i = 0; i < sprite.length; i++) {
			sprite[i] = tmp[0][i];
		}
		
		// Start rectangle
		collisionBounds = new Rectangle(x, y, allTexture.getWidth() / 3, allTexture.getHeight());
		
		// Play Spawn Sound
		assetManager.get("data/audio/sound/bombLaunching.wav", Sound.class).play(1.0f);
		
		// Get Array
		listOfExplosion = playState.getExplosionList();
	}
	
	// Update
	@Override
	public void update(float dt) {
		// Check if Dead
		if (healthbar <= 0) {
			isDead = true;
			listOfExplosion.add(new Explosion(this.x, this.y, 0, 0, playState, assetManager));
			return;
		}
		
		// Update Movement
		x += dx;
		y += dy;
		collisionBounds.set(x, y, 96f / 3f, 33);
		
		// Off Screen Check
		checkOffScreen();
	}

	// Render
	@Override
	public void draw(SpriteBatch spriteBatch) {
		spriteBatch.draw(sprite[0].getTexture(), x, y, 32, 33, 0, 0, 32, 33, false, true);	
	}
	
	// Check if off screen
	private void checkOffScreen() {
		if (y < -34) {
			isDead = true;
		}
	}
	
	public int getDamageValue() {
		return damageValue;
	}

}
