package com.jb.gameobjects.enemies;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.jb.gameobjects.GameObjects;
import com.jb.gamestates.PlayState;
import com.jb.level.level1.Level1;

public class KamikazeAlien extends GameObjects {

	// Graphics
	private TextureRegion[] sprite;

	// GamePlay
	private int damageValue;
	private boolean isOffScreen;

	// Physics
	private float maxSpeed;

	// Get Arrays
	private PlayState playState;
	private Level1 level1;

	public KamikazeAlien(float x, float y, float dx, float dy, int damageValue, Level1 level1,
			AssetManager assetManager) {
		super(x, y, dx, dy, assetManager);

		// GamePlay
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.damageValue = damageValue;
		this.level1 = level1;
		this.assetManager = assetManager;
		healthbar = 100;
		isDead = false;

		// Limits
		maxSpeed = 20;

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
	}
	
	// Update
	@Override
	public void update(float dt) {
		// Check if Dead
		if (healthbar <= 0) {
			isDead = true;
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
			isOffScreen = true;
		}
	}
	
	// Getter
	public boolean isOffScreen() {
		return isOffScreen;
	}

}
