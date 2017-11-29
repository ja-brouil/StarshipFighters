package com.jb.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jb.gameobjects.player.Player;
import com.jb.handler.GameStateManager;

public class PlayState extends GameState{
	
	private ShapeRenderer sRenderer;
	private Player player;

	public PlayState(GameStateManager gsm) {
		super(gsm);
		init();
		
	}
	
	@Override
	public void init() {
		sRenderer = new ShapeRenderer();
		player = new Player(0, 0, 0, 0);
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
		player.draw(sRenderer);
		spriteBatch.end();
		
	}

	@Override
	public void dispose() {
		spriteBatch.dispose();
		
	}

	
}
