package com.jb.HUD;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jb.gameobjects.player.Player;
import com.jb.gamestates.PlayState;
import com.jb.utilities.graphics.RadialSprite;

public class HealthBar {
	
	// Asset Manager
	private AssetManager assetManager;
	
	// Graphics
	private String healthBarPathName = "data/hud/HealthBar.png";
	private Texture healthGraphic;
	private TextureRegion healthGraphicTextureRegion;
	private RadialSprite radialSprite;
	
	// Position
	private float x,y;
	
	// Math
	private float angle;
	
	// GamePlay
	private float currentHealth;
	private float totalHealth;
	private float destinationHealth;
	private float healthChangeSpeed;

	// Player Access
	private Player player;
	
	public HealthBar(PlayState playState, float x, float y) {
		this.x = x;
		this.y = y;
		player = playState.getPlayer();
		assetManager = playState.getAssetManager();

		// Set Health Graphic
		assetManager.load(healthBarPathName, Texture.class);
		assetManager.finishLoading();
		healthGraphic = assetManager.get(healthBarPathName, Texture.class);	
		
		// Set up Radial Sprite
		healthGraphicTextureRegion = new TextureRegion(healthGraphic);
		radialSprite = new RadialSprite(healthGraphicTextureRegion);
		
		// Setup health
		currentHealth = player.getHealthBar();
		totalHealth = player.getHealthBar();
		destinationHealth = player.getHealthBar();
		healthChangeSpeed = 50;
	}
	
	// Update
	public void update(float dt) {
		
		// Check for change in HP
		destinationHealth = player.getHealthBar();
		if (currentHealth < destinationHealth) {
			currentHealth += healthChangeSpeed * dt;
			if (currentHealth > destinationHealth) {
				currentHealth = destinationHealth;
			}
		} else if (currentHealth > destinationHealth) {
			currentHealth -= healthChangeSpeed * dt;
			if (currentHealth < destinationHealth) {
				currentHealth = destinationHealth;
			}
		}
		
		// Get Angle to draw
		angle = 360 * (1 - (currentHealth / totalHealth));
	}
	
	// Render
	public void render(SpriteBatch spriteBatch) {
		radialSprite.draw(spriteBatch, x, y, angle);
	}
}
