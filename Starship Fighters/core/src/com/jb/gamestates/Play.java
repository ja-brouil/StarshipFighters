package com.jb.gamestates;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.jb.handler.GameStateManager;

public class Play extends GameState{
	
	private BitmapFont font = new BitmapFont();

	public Play(GameStateManager gsm) {
		super(gsm);
		
	}

	@Override
	public void handleInput() {
		
		
	}

	@Override
	public void update(float dt) {
		
		
	}

	@Override
	public void render() {
		
		// Test for font drawing
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		font.draw(spriteBatch, "Test", 100, 100);
		spriteBatch.end();
		
	}

	@Override
	public void dispose() {
		
		
	}

	
}
