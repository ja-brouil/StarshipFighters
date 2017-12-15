package com.jb.HUD;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class HealthBar extends HUD {
	
	private Color healthBarColor, healthBarOutline;
	private ShapeRenderer shapeRenderer;
	private float width, height, healthLeft;

	public HealthBar(float x, float y, float width, float height, boolean showHUDElement) {
		super(x, y, showHUDElement);
		
		this.x = x;
		this.y = y;
		this.showHUDElement = showHUDElement;
		this.height = height;
		this.width = width;
		
		// Start the box
		shapeRenderer = new ShapeRenderer();

		// Start Color
		healthBarColor = new Color(Color.GREEN);
		healthBarOutline = new Color(Color.WHITE);
		
	}

	@Override
	public void update(float dt) {
		

	}

	@Override
	public void draw(SpriteBatch spriteBatch) {
		
		// Draw HUD Element if it needs to be drawn
		if (showHUDElement) {
			// Draw Outline
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(healthBarOutline);
			shapeRenderer.rect(x, y, width, height);
			shapeRenderer.end();
			
			// Fill Rectangle
			shapeRenderer.begin(ShapeType.Filled);;
			shapeRenderer.setColor(healthBarColor);
			shapeRenderer.rect(x, y, healthLeft, height);
			shapeRenderer.end();
			//shapeRenderer.dispose(); Read up when/where should this be disposed	
		}
		
	}
	
	// Width to fill is the amount of HP left
	public void setHealthLeft(float healthLeft) {
		this.healthLeft = healthLeft;
	}

}
