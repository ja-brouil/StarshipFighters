package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.jb.handler.GameStateManager;

public class Play extends GameState{
	

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
		// Clear screen to Black Background
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		// Play State Draw
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		spriteBatch.end();
		
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		
	}

	
}
