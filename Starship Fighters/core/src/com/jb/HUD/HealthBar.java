package com.jb.HUD;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class HealthBar extends HUD {
	
	private Color healthBarColor;
	private ShapeRenderer shapeRenderer;
	private float height, healthLeft, width;

	public HealthBar(float x, float y, float healthLeft, float height, boolean showHUDElement) {
		super(x, y, showHUDElement);
		
		this.x = x;
		this.y = y;
		this.showHUDElement = showHUDElement;
		this.height = height;
		this.healthLeft = healthLeft;
		width = healthLeft;
		

		// Start Color
		healthBarColor = new Color(Color.GREEN);
		
		// Shape Renderer
		shapeRenderer = new ShapeRenderer();
		
		
	}

	@Override
	public void update(float dt) {
		
		// Set Limit
		// Health Bar is offset by 20 pixels
		if (healthLeft < 0) {
			healthLeft = 0;
		}

	}

	// This Method should be used but I don't have any custom HUD elements so we will just use LibGDX box + font capabilities
	public void draw(SpriteBatch spriteBatch) {
		
		// Draw HUD Element if it needs to be drawn
		if (showHUDElement) {

			shapeRenderer.begin(ShapeType.Filled);
			shapeRenderer.setColor(healthBarColor);
			shapeRenderer.rect(x, y, healthLeft - 1, height);
			shapeRenderer.end();
			
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.WHITE);
			shapeRenderer.rect(x, y, width, height);
			shapeRenderer.end();
		}
		
	}
	
	
	// Width to fill is the amount of HP left
	public void setHealthLeft(float healthLeft) {
		this.healthLeft += healthLeft;
	}

}
