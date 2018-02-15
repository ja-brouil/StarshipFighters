package com.jb.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jb.gamestates.PlayState;
import com.jb.main.Game;

public class LevelTransition extends Level{
	
	// Levels
	private Level previousLevel;
	private Level nextLevel;
	
	// Graphics
	private String blackArtPath = "data/transitions/BlackTransitionSquare.png";
	private Texture transitionTexture;

	// Timers
	private float timer;
	private float maxTime;
	
	// Size
	private float width;
	private float height;
	
	// Speed
	private float drawingSpeed;
	
	// Boolean
	private boolean active;
	
	public LevelTransition(PlayState playState, AssetManager assetManager, Level previousLevel, Level nextLevel) {
		super(playState, assetManager);
		
		// Set Levels
		this.previousLevel = previousLevel;
		this.nextLevel = nextLevel;
		
		// Set sleep
		active = false;
		
		// Graphics
		assetManager.load(blackArtPath, Texture.class);
		assetManager.finishLoading();
		transitionTexture = assetManager.get(blackArtPath, Texture.class);
		
		// Timers
		timer = 0;
		maxTime = 4;
		drawingSpeed = 75;
	}

	@Override
	public void update(float dt) {
		timer += Gdx.graphics.getDeltaTime();
		
		if (timer >= maxTime) {
			playState.getLevelManager().setLevel(nextLevel);
		}
		
		width += drawingSpeed * Gdx.graphics.getDeltaTime();
		height += drawingSpeed * Gdx.graphics.getDeltaTime();
		
		if (width >= Game.WIDTH) {
			width = Game.WIDTH;
		}
		
		if (height >= Game.HEIGHT) {
			height = Game.HEIGHT;
		}
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		spriteBatch.draw(transitionTexture, 0, 0, width, height);
	}
	
	public void setActiveStatus(boolean active) {
		this.active = active;
	}
	
	public boolean getActiveStatus() {
		return active;
	}

}
