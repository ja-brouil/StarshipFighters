package com.jb.gamestates;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jb.main.GameMain;

public abstract class GameState {

	protected GameState gsm;
	protected GameMain gameMain;
	
	protected SpriteBatch spriteBatch;
	protected OrthographicCamera cam;
	protected OrthographicCamera hudcam;
	
	
	public GameState(GameState gsm, GameMain gameMain, SpriteBatch spriteBatch, OrthographicCamera cam,
			OrthographicCamera hudcam) {
		this.gsm = gsm;
		this.gameMain = gameMain;
		this.spriteBatch = spriteBatch;
		this.cam = cam;
		this.hudcam = hudcam;
	}
	
	
	
	
	
}
