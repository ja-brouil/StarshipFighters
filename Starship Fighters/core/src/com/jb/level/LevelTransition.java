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
	private String blackArtPath = "data/transitions/blacktransition.png";
	private Texture transitionTexture;

	// Timers
	private float timer;
	private float maxTime;
	
	// Size
	private float x, y;
	
	// Speed
	private float alpha;
	
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
		maxTime = 14;
	}

	@Override
	public void update(float dt) {
		// Timer
		timer += Gdx.graphics.getDeltaTime();
		
		if (timer >= maxTime) {
			playState.getLevelManager().setLevel(nextLevel);
		}
		
		// Set Alpha
		if (timer < maxTime / 2) {
			alpha = timer / (maxTime / 2);
		} else {
			alpha = 2 - (timer / (maxTime / 2));
		}
		
	}

	@Override
	public void render(SpriteBatch spriteBatch) {	
		if (timer < maxTime / 2) {
			previousLevel.render(spriteBatch);
		} else {
			nextLevel.render(spriteBatch);
		}
		spriteBatch.setColor(0,0,0,alpha);
		spriteBatch.begin();
		spriteBatch.draw(transitionTexture, x, y, Game.WIDTH, Game.HEIGHT);
		spriteBatch.end();
		spriteBatch.setColor(1,1,1,1);
	}
	
	public void setActiveStatus(boolean active) {
		this.active = active;
	}
	
	public boolean getActiveStatus() {
		return active;
	}

}
