package com.jb.gameobjects.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jb.animation.Animator;
import com.jb.assetmanagers.audio.SoundManager;
import com.jb.gameobjects.GameObjects;

public class Explosion extends GameObjects {
	
	// Graphics
	private String pathName;
	private Animator explosionAnimation;
	private float animationTimer;
	private boolean explosionIsDone;
	
	// SFX
	private static String explosionSoundPathName = "data/audio/sound/Bomb Explosion.wav";
	private static String explosionSoundName = "Explosion Hit";
	
	static {
		SoundManager.addSound(explosionSoundPathName, explosionSoundName);
	}

	public Explosion(float x, float y, float dx, float dy) {
		super(x, y, dx, dy);
		
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		
		// Pathname for graphics
		pathName = "data/hit_and_explosions/explosion.png";
		
		// Start Explosion
		init();
		
	}
	
	// Initialize
	private void init() {
		// Start explosion animation
		explosionAnimation = new Animator(4, 2, pathName, 4, 2, 1f/40f);
		
		// Set Initial Boolean to false
		explosionIsDone = false;
		
		// Start SFX
		SoundManager.playSound(explosionSoundName);
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
