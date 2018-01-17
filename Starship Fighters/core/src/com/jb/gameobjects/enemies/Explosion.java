package com.jb.gameobjects.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jb.animation.Animator;
import com.jb.assetmanagers.audio.SoundManager;
import com.jb.gameobjects.GameObjects;
import com.jb.gamestates.PlayState;

public class Explosion extends GameObjects {
	
	// PlayState
	private PlayState playState;

	// Graphics
	private String pathName;
	private Animator explosionAnimation;
	private float animationTimer;
	private boolean explosionIsDone;

	// SFX || Default || Player
	private SoundManager soundManager;
	private String explosionSoundPathName = "data/audio/sound/Bomb Explosion.wav";
	private String explosionSoundName = "Explosion Hit";
	private String explosionPlayerPathName = "data/audio/sound/No Damage.wav";
	private String explosionPlayerName = "Player Hit";

	// Default Explosion
	public Explosion(float x, float y, float dx, float dy, PlayState playState) {
		super(x, y, dx, dy);

		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.playState = playState;

		// Pathname for graphics
		pathName = "data/hit_and_explosions/explosion.png";

		// Start Explosion
		init();

	}

	// Custom Explosion
	public Explosion(float x, float y, float dx, float dy, String pathName, String explosionSoundCustomPathName,
			String explosionSoundCustomName, int cols, int rows, int colCut, int rowCut, float frameLengthTime, PlayState playState) {
		super(x, y, dx, dy);
		
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.playState = playState;
		soundManager = playState.getGSM().getGame().getSoundManager();

		this.pathName = pathName;
		init(cols, rows, colCut, rowCut, frameLengthTime);

	}
	
	// Boss Hit
	public Explosion(float x, float y, float dx, float dy, String pathName, String explosionSoundCustomPathName,
			String explosionSoundCustomName, int cols, int rows, int colCut, int rowCut, float frameLengthTime, boolean playSound, PlayState playState) {
		super(x, y, dx, dy);
		
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.playState = playState;
		soundManager = playState.getGSM().getGame().getSoundManager();

		this.pathName = pathName;
		init(cols, rows, colCut, rowCut, frameLengthTime, playSound);

	}

	// Initialize
	private void init() {		

		// Start explosion animation
		explosionAnimation = new Animator(4, 2, pathName, 4, 2, 1f / 40f);

		// Set Initial Boolean to false
		explosionIsDone = false;

		// Start SFX
		soundManager = playState.getGSM().getGame().getSoundManager();
		soundManager.addSound(explosionSoundPathName, explosionSoundName);
		soundManager.addSound(explosionPlayerPathName, explosionPlayerName);
		soundManager.playSound(explosionSoundName, 1.0f);
		

	}
	
	// Player Hit
	private void init(int cols, int rows, int colCut, int rowCut, float frameLengthTime) {
		
		// Start explosion animation
		explosionAnimation = new Animator(cols, rows, pathName, colCut, rowCut, frameLengthTime);

		// Set Initial Boolean to false
		explosionIsDone = false;

		// Start SFX
		soundManager.addSound(explosionSoundPathName, explosionSoundName);
		soundManager.addSound(explosionPlayerPathName, explosionPlayerName);
		soundManager.playSound(explosionPlayerName, 0.3f);
		
	}
	
	// Boss Hit
	private void init(int cols, int rows, int colCut, int rowCut, float frameLengthTime, boolean playSound) {
		
		// Start explosion animation
		explosionAnimation = new Animator(cols, rows, pathName, colCut, rowCut, frameLengthTime);

		// Set Initial Boolean to false
		explosionIsDone = false;

		// Start SFX
		if (playSound) {
			soundManager.playSound(explosionPlayerName, 0.3f);
		}	
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

	@Override
	public void update(float dt, boolean xWrap, boolean yWrap) {
		// TODO Auto-generated method stub
		
	}
	
	// Dispose
	public void dispose() {
		explosionAnimation.dispose();
	}

}
