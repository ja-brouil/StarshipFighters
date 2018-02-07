package com.jb.HUD;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jb.gameobjects.player.Player;
import com.jb.gamestates.PlayState;

public class HealthBar {
	
	// Asset Manager
	private AssetManager assetManager;
	
	// Graphics
	private String healthBarPathName = "data/hud/HealthBar.png";
	private Texture healthGraphic;
	private float x,y;
	
	// GamePlay
	private float healthRemaining;
	private float totalHealth;

	// Player Access
	private Player player;
	
	// Timers
	private float timer;
	private float maxTime;
	
	public HealthBar(PlayState playState, float x, float y) {
		this.x = x;
		this.y = y;
		player = playState.getPlayer();
		assetManager = playState.getAssetManager();
		healthRemaining = player.getHP();
		totalHealth = player.getHP();
		
		// Set Health Graphic
		assetManager.load(healthBarPathName, Texture.class);
		assetManager.finishLoading();
		healthGraphic = assetManager.get(healthBarPathName, Texture.class);
		
		// Set Max time
		maxTime = 0.5f;
		
	}
	
	
	public void update(float dt) {
		
		// Update Timer
		timer += dt;
		
		// Update Health
		healthRemaining = player.getHP();
		
	}
	
	public void render(SpriteBatch spriteBatch) {
		float drawTime = timer / maxTime;
		if (timer >= 0.5) {
			spriteBatch.draw(healthGraphic, x, y, healthGraphic.getWidth() * (healthRemaining / totalHealth), healthGraphic.getHeight(), 15, 15, (int) (healthGraphic.getWidth() * (healthRemaining / totalHealth)), (int) healthGraphic.getHeight(), false, false);
		} else {
			spriteBatch.draw(healthGraphic, x, y, healthGraphic.getWidth() * (healthRemaining / totalHealth), healthGraphic.getHeight(), 15, 15, (int) (healthGraphic.getWidth() * (healthRemaining / totalHealth) * drawTime), (int) healthGraphic.getHeight(), false, false);
		}
	}
}
