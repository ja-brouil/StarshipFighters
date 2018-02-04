package com.jb.gameobjects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jb.animation.Animator;
import com.jb.gameobjects.GameObjects;

public class PlayerHit extends GameObjects{
	
	// Graphics
	private Animator playerHitAnimation;
	private String textureFilePath = "data/hit_and_explosions/impactHit.png";
	private boolean playerHitAnimationDone;
	private float animationTimer;
	
	// Sound
	private Sound playerHitSound;
	private String soundPathNamePlayerHit = "data/audio/sound/No Damage.wav";
	

	public PlayerHit(float x, float y, float dx, float dy, int cols, int rows, int colCut, int rowCut, float frameLengthTime, AssetManager assetManager) {
		super(x, y, dx, dy, assetManager);
		
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.assetManager = assetManager;
		
		init(cols, rows, colCut, rowCut, frameLengthTime);
	}
	
	// Initialize
	private void init(int cols, int rows, int colCut, int rowCut, float frameLengthTime) {
		// Get Animation for hit
		playerHitAnimation = new Animator(cols, rows, textureFilePath, colCut, rowCut, frameLengthTime, assetManager);
		
		// Set Initial Playback to false
		playerHitAnimationDone = false;
		
		// Get SFX for Hit and Play Sound
		soundPathNamePlayerHit = "data/audio/sound/No Damage.wav";
		playerHitSound = assetManager.get(soundPathNamePlayerHit, Sound.class);
		playerHitSound.play(1.0f);
	}
	
	// Update
	@Override
	public void update(float dt) {
		// Check if animation is done
		if (playerHitAnimation.getAnimationFrames().isAnimationFinished(animationTimer)) {
			playerHitAnimationDone = true;
		}
	}

	// Render
	@Override
	public void draw(SpriteBatch spriteBatch) {
		animationTimer += Gdx.graphics.getDeltaTime();
		spriteBatch.draw(playerHitAnimation.getAnimationFrames().getKeyFrame(animationTimer, false), x, y);
		
	}
	
	// Getter
	public boolean isPlayerHitAnimationDone() {
		return playerHitAnimationDone;
	}

}
