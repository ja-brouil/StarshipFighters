package com.jb.gameobjects.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jb.animation.Animator;
import com.jb.assetmanagers.audio.SoundManager;
import com.jb.gameobjects.GameObjects;
import com.jb.gamestates.PlayState;

public class Explosion extends GameObjects {
	
	// PlayState
	private PlayState playState;
	
	// Asset Manager
	private AssetManager assetManager;

	// Graphics
	private String explosionPathName = "data/hit_and_explosions/explosion.png";
	private Animator explosionAnimation;
	private float animationTimer;
	private boolean explosionIsDone;

	// SFX Enemy Explosion
	private String explosionSoundPathName = "data/audio/sound/Bomb Explosion.wav";
	private Sound enemyExplosionSound;

	// Default Explosion
	public Explosion(float x, float y, float dx, float dy, PlayState playState, AssetManager assetManager) {
		super(x, y, dx, dy, assetManager);

		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.playState = playState;
		this.assetManager = assetManager;


		// Start Explosion
		init();

	}

	// Initialize
	private void init() {		

		// Start explosion animation
		explosionAnimation = new Animator(4, 2, explosionPathName, 4, 2, 1f / 40f, assetManager);

		// Set Initial Boolean to false
		explosionIsDone = false;
		

	}

	public void update(float dt) {
		// Check if animation is done playing
		if (explosionAnimation.getAnimationFrames().isAnimationFinished(animationTimer)) {
			explosionIsDone = true;
		}
	}

	// Draw Explosion
	public void draw(SpriteBatch spriteBatch) {
		animationTimer += Gdx.graphics.getDeltaTime();
		spriteBatch.draw(explosionAnimation.getAnimationFrames().getKeyFrame(animationTimer, false), x, y);
	}

	// Getters and Setters
	public void setExplosionStatus(boolean b) {
		explosionIsDone = b;
	}

	public boolean getExplosionStatus() {
		return explosionIsDone;
	}


}
