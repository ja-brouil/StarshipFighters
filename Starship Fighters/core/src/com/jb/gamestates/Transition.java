package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.jb.main.Game;

public class Transition extends GameState{
	
	// GameState
	private GameState previousState;
	private GameState nextState;
	private TransitionType transitionType;
	
	// Transition Type
	public enum TransitionType {
		BLACK_FADE,
		WHITE_FADE;
	};
	
	// Asset Manager
	private AssetManager assetManager;
	
	// Transition Art
	private String blackFade = "data/transitions/blacktransition.png";
	private Texture blackFadeTexture;
	
	// Timers
	private float maxTime;
	private float timer;
	private float alpha;
	
	public Transition(GameStateManager gsm, GameState previousState, GameState nextState, TransitionType transitionType) {
		super(gsm);
		this.transitionType = transitionType;
		this.nextState = nextState;
		this.previousState = previousState;
		this.transitionType = transitionType;
		
		// Start Transition
		init();
	}

	@Override
	public void init() {
		
		// Black Fade
		if (transitionType == TransitionType.BLACK_FADE) {
			maxTime = 3;
		}
		
		// Asset Manager
		assetManager = gsm.getGame().getAssetManager();
		
		// Load Black Screen
		assetManager.load(blackFade, Texture.class);
		assetManager.finishLoading();
		blackFadeTexture = assetManager.get(blackFade, Texture.class);
	}

	@Override
	public void handleInput() {
		// Quit
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void update(float dt) {
		// Update Timer
		timer += dt;
		
		// Handle Input
		handleInput();
		
		// Update to the next GameState
		if (timer >= maxTime) {
			gsm.setState(nextState);
		}
	}

	@Override
	public void render() {
		// Black Type
		if (transitionType == TransitionType.BLACK_FADE) {
			// Alpha Timer | Divided by two because we want half the time to fade in and half the time to fade out
			if (timer < maxTime / 2) {
				alpha = timer / (maxTime / 2);
				previousState.render();
			} else {
				alpha = 2 - (timer / (maxTime / 2));
				nextState.render();
			}
			
			// Draw Transition
			spriteBatch.setColor(0,0,0,alpha);
			spriteBatch.setProjectionMatrix(cam.combined);
			spriteBatch.begin();
			spriteBatch.draw(blackFadeTexture, 0, 0, Game.WIDTH, Game.HEIGHT);
			spriteBatch.end();
			spriteBatch.setColor(1,1,1,1);
		}
		
	}

	@Override
	public void dispose() {
		System.gc();
	}
	
	// Getter
	public float getAlpha() {
		return alpha;
	}
}
